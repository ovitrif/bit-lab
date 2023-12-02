package tech.masivo.bitlab.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import tech.masivo.bitlab.data.model.TransactionResult
import tech.masivo.bitlab.data.sources.ApiClient
import javax.inject.Inject

@HiltViewModel
class BlockDetailViewModel @Inject constructor(
    private val apiClient: ApiClient,
) : ViewModel() {
    private val _uiState = MutableStateFlow(initUiState())
    val uiState: StateFlow<UiState> = _uiState

    fun refresh(blockId: String) {
        getTransactions(blockId)
    }

    private fun getTransactions(blockId: String) {
        viewModelScope.launch {
            val transactions = apiClient.mempool.getBlockTransactions(blockId)
            _uiState.emit(
                _uiState.value.copy(transactions = transactions)
            )
        }
    }

    // REGION: State
    private fun initUiState(): UiState {
        return UiState()
    }

    data class UiState(
        val transactions: List<TransactionResult> = emptyList(),
    )
}