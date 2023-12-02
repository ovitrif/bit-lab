package tech.masivo.bitlab.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import tech.masivo.bitlab.data.model.BlockResult
import tech.masivo.bitlab.data.sources.ApiClient
import tech.masivo.bitlab.data.sources.WebSocketClient
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiClient: ApiClient,
    private val webSocketClient: WebSocketClient,
) : ViewModel() {
    private val _uiState = MutableStateFlow(initUiState())
    val uiState: StateFlow<UiState> = _uiState

    init {
        startSocket()
    }

    private fun startSocket() {
        viewModelScope.launch {
            val socketChannel = webSocketClient.startSocket()
            for (socketUpdate in socketChannel) {
                Log.d("_WS_", socketUpdate.toString())
            }
        }
    }

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

    override fun onCleared() {
        webSocketClient.stopSocket()
        super.onCleared()
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