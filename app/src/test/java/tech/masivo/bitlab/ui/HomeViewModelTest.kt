package tech.masivo.bitlab.ui

import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class HomeViewModelTest {
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModel(
            mempoolWebSocketClient = mock(),
        )
    }

    @Test
    fun `should fetch data on init`() {
        TODO("Not yet implemented")
    }

    @Test
    fun `should emit data updates to ui state`() {
        TODO("Not yet implemented")
    }

    @Test
    fun `should not emit empty blocks from updates`() {
        TODO("Not yet implemented")
    }
}