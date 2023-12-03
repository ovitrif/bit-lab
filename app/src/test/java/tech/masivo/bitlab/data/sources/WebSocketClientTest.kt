package tech.masivo.bitlab.data.sources

import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class WebSocketClientTest {
    private lateinit var client: WebSocketClient

    @Before
    fun setUp() {
        client = WebSocketClient(
            okHttpClient = mock(),
            json = mock()
        )
    }

    @Test
    fun `should connect websocket when subscribing to events`() {
        TODO("Not yet implemented")
    }

    @Test
    fun `should send init message on connection open`() {
        TODO("Not yet implemented")
    }

    @Test
    fun `should send want message on connection open`() {
        TODO("Not yet implemented")
    }

    @Test
    fun `should emit update event with parsed result on socket message`() {
        TODO("Not yet implemented")
    }

    @Test
    fun `should emit abort event on socket closing`() {
        TODO("Not yet implemented")
    }

    @Test
    fun `should emit close event on socket failure`() {
        TODO("Not yet implemented")
    }

    @Test
    fun `should disconnect websocket when unsubscribing to events`() {
        TODO("Not yet implemented")
    }
}