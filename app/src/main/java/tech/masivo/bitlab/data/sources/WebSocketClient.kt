package tech.masivo.bitlab.data.sources

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

class WebSocketClient @Inject constructor(
    private val okHttpClient: OkHttpClient,
) {
    private var mempoolBaseUrl: String = "wss://mempool.space/api/v1/ws"

    private fun connect(
        baseUrl: String,
        listener: WebSocketListener
    ): WebSocket {
        return okHttpClient.newWebSocket(
            Request.Builder().url(baseUrl).build(),
            listener
        )
    }

    fun subscribe(): Flow<SocketEvent> = callbackFlow {
        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                val initAction = """{ "action": "init" }"""
                val wantAction = """{ "action": "want", "data": ["blocks", "mempool-blocks"] }"""

                webSocket.send(initAction)
                webSocket.send(wantAction)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                trySend(SocketEvent.Update(text))
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                trySend(SocketEvent.Abort(code, reason))
                webSocket.close(NORMAL_CLOSURE_STATUS, reason)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                trySend(SocketEvent.Close(t))
            }
        }
        val socket = connect(mempoolBaseUrl, listener)

        awaitClose {
            socket.close(NORMAL_CLOSURE_STATUS, APP_DISCONNECTED_REASON)
        }
    }

    sealed interface SocketEvent {
        data class Update(val message: String) : SocketEvent
        data class Abort(val code: Int, val reason: String) : SocketEvent
        data class Close(val throwable: Throwable) : SocketEvent
    }

    companion object {
        const val NORMAL_CLOSURE_STATUS = 1000
        const val APP_DISCONNECTED_REASON = "APP_DISCONNECTED"
    }
}