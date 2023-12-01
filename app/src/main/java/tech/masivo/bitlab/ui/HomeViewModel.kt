package tech.masivo.bitlab.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import tech.masivo.bitlab.data.model.BlockResult
import tech.masivo.bitlab.data.sources.ApiClient
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiClient: ApiClient,
) : ViewModel() {
    private val _uiState = MutableStateFlow(initUiState())
    val uiState: StateFlow<UiState> = _uiState

    private fun getBlocks() {
        viewModelScope.launch {
            val blocks = apiClient.mempool.getBlocks()
            _uiState.emit(
                _uiState.value.copy(blocks = blocks)
            )
        }
    }

    private fun onBlockClick(id: String) {
        TODO("Clicked block with id: $id")
    }

    // REGION: State
    private fun initUiState(): UiState {
        return UiState(
            getBlocks = ::getBlocks,
            onBlockClick = ::onBlockClick,
        )
    }

    data class UiState(
        val title: String = "Bitcoin Blocks Explorer",
        val getBlocks: () -> Unit = {},
        val onBlockClick: (id: String) -> Unit = {},
        val blocks: List<BlockResult> = emptyList(),
    )
}