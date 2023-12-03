package tech.masivo.bitlab.ui

import androidx.compose.foundation.clickable
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
    transactions: List<BlockDetailViewModel.TransactionUiState>,
) {
    Column {
        Text(
            text = "Block ${blockId.trimId()}",
            style = MaterialTheme.typography.titleLarge,
        )
        TransactionsListUI(items = transactions)
    }
}

@Composable
private fun TransactionsListUI(
    modifier: Modifier = Modifier,
    items: List<BlockDetailViewModel.TransactionUiState> = emptyList(),
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
            key = { it.id },
        ) {
            Card(
                shape = CardDefaults.elevatedShape,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable(onClick = it::toggle),
            ) {
                InfoRow(
                    label = it.id.trimId(),
                )
                if (it.isExpanded.value) {
                    if (it.ins.isNotEmpty()) {
                        Text(text = "Inbound Transfers:")
                        it.ins.forEach { ins ->
                            InfoRow(
                                label = ins.address.trimId(),
                                value = "${ins.value} sat",
                            )
                        }
                    } else {
                        Text(text = "Mined")
                    }
                    Text(text = "Outbound Transfers:")
                    it.outs.forEach { ins ->
                        InfoRow(
                            label = ins.address.trimId(),
                            value = "${ins.value} sat",
                        )
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