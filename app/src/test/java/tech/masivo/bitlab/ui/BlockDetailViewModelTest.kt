package tech.masivo.bitlab.ui

import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class BlockDetailViewModelTest {
    private lateinit var viewModel: BlockDetailViewModel

    @Before
    fun setUp() {
        viewModel = BlockDetailViewModel(
            mempoolRestApi = mock()
        )
    }

    @Test
    fun `fetchDetails should fetch transactions for given blockId`() {
        TODO("Not yet implemented")
    }

    @Test
    fun `fetchDetails should emit ui state on api result`() {
        TODO("Not yet implemented")
    }

    @Test
    fun `fetchDetails should map api transactions data to uiState`() {
        TODO("Assert that api transaction data is correctly mapped to the ui state model")
    }

    @Test
    fun `transaction uiState should switch expand value on toggle`() {
        TODO("Not yet implemented")
    }
}