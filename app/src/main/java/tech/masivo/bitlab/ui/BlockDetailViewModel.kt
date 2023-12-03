package tech.masivo.bitlab.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import tech.masivo.bitlab.data.model.TransactionResult
import tech.masivo.bitlab.data.sources.MempoolRestApi
import tech.masivo.bitlab.ui.utils.satToBtc
import javax.inject.Inject

@HiltViewModel
class BlockDetailViewModel @Inject constructor(
    private val mempoolRestApi: MempoolRestApi,
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun fetchDetails(blockId: String) {
        getTransactions(blockId)
    }

    private fun getTransactions(blockId: String) {
        viewModelScope.launch {
            val transactions = mempoolRestApi.getBlockTransactions(blockId)
                .map { it.toUiState() }
            _uiState.emit(
                _uiState.value.copy(transactions = transactions)
            )
        }
    }

    private fun TransactionResult.toUiState() = TransactionUiState(
        id = txid,
        fee = fee,
        ins = vin.mapNotNull {
            it.prevout?.let { prevOut ->
                TransferUiState(
                    address = prevOut.scriptpubkeyAddress.orEmpty(),
                    value = prevOut.value.satToBtc(),
                )
            }
        },
        outs = vout.mapNotNull {
            it.scriptpubkeyAddress?.let { address ->
                TransferUiState(
                    address = address,
                    value = it.value.satToBtc(),
                )
            }
        },
    )

    data class UiState(
        val transactions: List<TransactionUiState> = emptyList(),
    )

    data class TransactionUiState(
        val id: String,
        val fee: Long,
        val ins: List<TransferUiState>,
        val outs: List<TransferUiState>,
    ) {
        private var _isExpanded = mutableStateOf(false)
        var isExpanded: State<Boolean> = _isExpanded

        fun toggle() {
            _isExpanded.value = !_isExpanded.value
        }
    }

    data class TransferUiState(
        val address: String,
        val value: Double,
    )
}