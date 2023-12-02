package tech.masivo.bitlab.data.sources

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import tech.masivo.bitlab.data.model.Block
import tech.masivo.bitlab.data.model.MempoolResult
import javax.inject.Inject

class WebSocketClient @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val json: Json,
) {
    private val eventsFlow: Flow<SocketEvent> by lazy { subscribe() }

    fun blocks(): Flow<List<Block>> = eventsFlow
        .filterIsInstance<SocketEvent.Update>()
        .mapNotNull { it.result.blocks }

    private fun connect(
        baseUrl: String,
        listener: WebSocketListener,
    ): WebSocket {
        return okHttpClient.newWebSocket(
            Request.Builder().url(baseUrl).build(),
            listener
        )
    }

    private fun subscribe(): Flow<SocketEvent> = callbackFlow {
        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                val initAction = """{ "action": "init" }"""
                val wantAction = """{"action":"want","data":["blocks","stats","mempool-blocks"]}"""

                webSocket.send(initAction)
                webSocket.send(wantAction)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                val parsedResult = json.decodeFromString<MempoolResult>(text)
                trySend(SocketEvent.Update(parsedResult))
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                trySend(SocketEvent.Abort(code, reason))
                webSocket.close(NORMAL_CLOSURE_STATUS, reason)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                trySend(SocketEvent.Close(t))
            }
        }
        val socket = connect(MEMPOOL_BASE_URL, listener)

        awaitClose {
            socket.close(NORMAL_CLOSURE_STATUS, APP_DISCONNECTED_REASON)
        }
    }

    sealed interface SocketEvent {
        data class Update(val result: MempoolResult) : SocketEvent
        data class Abort(val code: Int, val reason: String) : SocketEvent
        data class Close(val throwable: Throwable) : SocketEvent
    }

    companion object {
        const val MEMPOOL_BASE_URL: String = "wss://mempool.space/api/v1/ws"

        const val NORMAL_CLOSURE_STATUS = 1000
        const val APP_DISCONNECTED_REASON = "APP_DISCONNECTED"
    }
}