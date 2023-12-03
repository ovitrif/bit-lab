package tech.masivo.bitlab.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import tech.masivo.bitlab.data.model.Block
import tech.masivo.bitlab.data.model.Transaction
import tech.masivo.bitlab.data.sources.MempoolWebSocketClient
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mempoolWebSocketClient: MempoolWebSocketClient,
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    init {
        fetchLiveData()
    }

    private fun fetchLiveData() {
        viewModelScope.launch {
            mempoolWebSocketClient.data().collect {
                // Update blocks on ui only when we have new data
                val blocks = it.blocks ?: _uiState.value.blocks
                _uiState.value = _uiState.value.copy(
                    blocks = blocks,
                    transactions = it.transactions,
                )
            }
        }
    }

    data class UiState(
        val title: String = "Bitcoin Blocks Explorer",
        val blocks: List<Block> = emptyList(),
        val transactions: List<Transaction> = emptyList(),
    )
}