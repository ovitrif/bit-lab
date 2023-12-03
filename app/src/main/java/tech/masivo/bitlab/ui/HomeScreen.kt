package tech.masivo.bitlab.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import tech.masivo.bitlab.data.model.Block
import tech.masivo.bitlab.data.model.Transaction
import tech.masivo.bitlab.ui.components.InfoCard
import tech.masivo.bitlab.ui.components.InfoRow
import tech.masivo.bitlab.ui.theme.BitlabTheme
import tech.masivo.bitlab.ui.utils.asMegabytes
import tech.masivo.bitlab.ui.utils.formatRate
import tech.masivo.bitlab.ui.utils.formatTimestamp
import tech.masivo.bitlab.ui.utils.trimId

@Composable
fun HomeScreen(
    uiState: HomeViewModel.UiState,
    onBlockClick: (id: String) -> Unit = {},
) {
    Column {
        Text(
            text = uiState.title,
            style = MaterialTheme.typography.titleLarge,
        )
        Column {
            BlocksList(
                uiState.blocks,
                onBlockClick,
                modifier = Modifier.weight(.5f)
            )
            RecentTransactions(
                items = uiState.transactions,
                modifier = Modifier.weight(.5f)
            )
        }
    }
}

@Composable
private fun RecentTransactions(
    items: List<Transaction>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text("Recent Transactions")
        LazyColumn {
            items(items, key = { it.txid }) {
                InfoCard {
                    Row {
                        InfoRow(label = it.txid.trimId())
                        Spacer(Modifier.weight(1f))
                        InfoRow(label = "Fee:", value = "${it.rate.formatRate()} sat/vB")
                    }
                }
            }
        }
    }
}

@Composable
private fun BlocksList(
    blocks: List<Block>,
    onBlockClick: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        item { Text("Last ${blocks.size} OnChain Blocks") }
        items(blocks, key = { it.id }) {
            InfoCard(onClick = { onBlockClick(it.id) }) {
                InfoRow(label = it.id.trimId())
                Row {
                    InfoRow(label = "At:", value = it.timestamp.formatTimestamp())
                    InfoRow(label = it.size.asMegabytes())
                    InfoRow(label = it.txCount.toString(), value = "transactions")
                }
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    BitlabTheme {
        val uiState = HomeViewModel.UiState()
        HomeScreen(uiState)
    }
}