package tech.masivo.bitlab.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import tech.masivo.bitlab.data.model.Block
import tech.masivo.bitlab.data.sources.WebSocketClient
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val webSocketClient: WebSocketClient,
) : ViewModel() {
    private val _uiState = MutableStateFlow(initUiState())
    val uiState: StateFlow<UiState> = _uiState

    init {
        fetchLiveBlocks()
    }

    private fun fetchLiveBlocks() {
        viewModelScope.launch {
            webSocketClient.blocks().collect {
                _uiState.value = _uiState.value.copy(blocks = it)
            }
        }
    }

    private fun onBlockClick(id: String) {
        TODO("Clicked block with id: $id")
    }

    // REGION: State
    private fun initUiState(): UiState {
        return UiState(
            onBlockClick = ::onBlockClick,
        )
    }

    data class UiState(
        val title: String = "Bitcoin Blocks Explorer",
        val onBlockClick: (id: String) -> Unit = {},
        val blocks: List<Block> = emptyList(),
    )
}