package tech.masivo.bitlab.data.sources

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
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

    fun blocks(): Flow<SocketUpdate> = callbackFlow {
        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                val actionInit = """{ "action": "init" }"""
                val actionBlocks = """{ "action": "want", "data": ["mempool-blocks"] }"""

                webSocket.send(actionInit)
                webSocket.send(actionBlocks)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                trySend(SocketUpdate(text))
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                trySend(SocketUpdate(exception = SocketAbortedException()))
                webSocket.close(NORMAL_CLOSURE_STATUS, null)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                trySend(SocketUpdate(exception = t))
            }
        }
        val socket = connect(mempoolBaseUrl, listener)

        awaitClose {
            socket.close(NORMAL_CLOSURE_STATUS, null)
        }
    }

    data class SocketUpdate(
        val text: String? = null,
        val byteString: ByteString? = null,
        val exception: Throwable? = null,
    )

    class SocketAbortedException : Exception()

    companion object {
        const val NORMAL_CLOSURE_STATUS = 1000
    }
}