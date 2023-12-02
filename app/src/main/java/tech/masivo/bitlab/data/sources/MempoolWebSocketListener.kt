package tech.masivo.bitlab.data.sources

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

//TODO: convert to expose flow
@OptIn(DelicateCoroutinesApi::class)
class MempoolWebSocketListener(private val coroutineScope: CoroutineScope = GlobalScope) :
    WebSocketListener() {

    private val closureStatusCode: Int = 1000

    val socketEventChannel: Channel<SocketUpdate> = Channel(10)

    override fun onOpen(webSocket: WebSocket, response: Response) {
        val actionInit = """{ "action": "init" }"""
        val actionBlocks = """{ "action": "want", "data": ["mempool-blocks"] }"""

        webSocket.send(actionInit)
        webSocket.send(actionBlocks)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        coroutineScope.launch {
            socketEventChannel.send(SocketUpdate(text))
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        coroutineScope.launch {
            socketEventChannel.send(SocketUpdate(exception = SocketAbortedException()))
        }
        webSocket.close(closureStatusCode, null)
        socketEventChannel.close()
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        coroutineScope.launch {
            socketEventChannel.send(SocketUpdate(exception = t))
        }
    }
}

class SocketAbortedException : Exception()

data class SocketUpdate(
    val text: String? = null,
    val byteString: ByteString? = null,
    val exception: Throwable? = null,
)