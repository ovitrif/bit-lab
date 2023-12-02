package tech.masivo.bitlab.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tech.masivo.bitlab.data.model.TransactionResult
import tech.masivo.bitlab.data.model.TransactionStatusResult
import tech.masivo.bitlab.ui.components.InfoRow
import tech.masivo.bitlab.ui.theme.BitlabTheme

@Composable
fun BlockDetailScreen(
    blockId: String,
    viewModel: BlockDetailViewModel,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    if (uiState.value.transactions.isEmpty()) {
        Text(text = "Loading...")
    } else {
        BlockDetails(
            blockId = blockId,
            transactions = uiState.value.transactions,
        )
    }
}

@Composable
fun BlockDetails(
    blockId: String,
    transactions: List<TransactionResult>,
) {
    Column {
        Text(
            text = "Block ${blockId.takeLast(8)}",
            style = MaterialTheme.typography.titleLarge,
        )
        TransactionsListUI(items = transactions)
    }
}

@Composable
private fun TransactionsListUI(
    modifier: Modifier = Modifier,
    items: List<TransactionResult> = emptyList(),
) {
    val scrollState = rememberLazyListState()
    LazyColumn(
        userScrollEnabled = true,
        state = scrollState,
        modifier = Modifier.fillMaxHeight()
    ) {
        item { Text(text = "Last ${items.size} Transactions") }
        items(
            items = items,
            key = { it.txid },
        ) {
            Card(
                shape = CardDefaults.elevatedShape,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                InfoRow(
                    label = it.txid.takeLast(8),
                )
                InfoRow(
                    label = "Confirmed:",
                    value = it.status.confirmed.toString(),
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Composable
private fun BlockScreenPreview() {
    BitlabTheme {
        BlockDetails(
            blockId = "4",
            transactions = List(3) {
                TransactionResult(
                    txid = "$it",
                    fee = 0L,
                    status = TransactionStatusResult(
                        confirmed = it % 2 == 0,
                    ),
                )
            }
        )
    }
}