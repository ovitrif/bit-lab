package tech.masivo.bitlab.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import tech.masivo.bitlab.ui.components.InfoRow
import tech.masivo.bitlab.ui.theme.BitlabTheme
import tech.masivo.bitlab.ui.utils.trimId

@Composable
fun BlockDetailScreen(
    blockId: String,
    viewModel: BlockDetailViewModel,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    if (uiState.value.transactions.isEmpty()) {
        Text("Loading...")
    } else {
        BlockDetails(
            blockId = blockId,
            transactions = uiState.value.transactions,
        )
    }
}

@Composable
private fun BlockDetails(
    blockId: String,
    transactions: List<BlockDetailViewModel.TransactionUiState>,
) {
    Column {
        Text(
            text = "Block ${blockId.trimId()}",
            style = MaterialTheme.typography.titleLarge,
        )
        TransactionsList(items = transactions)
    }
}

@Composable
private fun TransactionsList(
    modifier: Modifier = Modifier,
    items: List<BlockDetailViewModel.TransactionUiState> = emptyList(),
) {
    LazyColumn(modifier.fillMaxHeight()) {
        item { Text("Last ${items.size} Transactions") }
        items(items, key = { it.id }) {
            Card(
                shape = CardDefaults.elevatedShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable(onClick = it::toggle),
            ) {
                InfoRow(label = it.id.trimId())
                it.fee.takeIf { v -> v > 0 }?.let { fee ->
                    InfoRow(label = "Fee:", value = "$fee sat")
                }
                if (it.isExpanded.value) {
                    if (it.inbounds.isNotEmpty()) {
                        Text("Inbound Transfers:")
                        it.inbounds.forEach { ins ->
                            InfoRow(
                                label = ins.address.trimId(),
                                value = "${ins.value} BTC",
                            )
                        }
                    } else {
                        Text("Mined")
                    }
                    Text("Outbound Transfers:")
                    it.outbounds.forEach { ins ->
                        InfoRow(label = ins.address.trimId(), value = "${ins.value} BTC")
                    }
                }
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
            transactions = emptyList()
        )
    }
}