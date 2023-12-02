package tech.masivo.bitlab.data.sources

import kotlinx.coroutines.channels.Channel
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import javax.inject.Inject

class WebSocketClient @Inject constructor(
    private val okHttpClient: OkHttpClient,
) {
    private var webSocket: WebSocket? = null
    private var mempoolBaseURL: String = "wss://mempool.space/api/v1/ws"

    private var webSocketListener: MempoolWebSocketListener? = null

    fun startSocket(): Channel<SocketUpdate> =
        with(MempoolWebSocketListener()) {
            startSocket(this)
            this@with.socketEventChannel
        }

    private fun startSocket(webSocketListener: MempoolWebSocketListener) {
        this.webSocketListener = webSocketListener
        webSocket = okHttpClient.newWebSocket(
            Request.Builder().url(mempoolBaseURL).build(),
            webSocketListener
        )
    }

    fun stopSocket() {
        try {
            webSocket?.close(NORMAL_CLOSURE_STATUS, null)
            webSocket = null
            webSocketListener?.socketEventChannel?.close()
            webSocketListener = null
        } catch (ex: Exception) {
            // TODO: Handle exception
        }
    }

    companion object {
        const val NORMAL_CLOSURE_STATUS = 1000
    }

}