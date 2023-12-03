package tech.masivo.bitlab.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import tech.masivo.bitlab.data.model.Block
import tech.masivo.bitlab.ui.components.InfoRow
import tech.masivo.bitlab.ui.theme.BitlabTheme
import tech.masivo.bitlab.ui.utils.asMegabytes
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
            BlocksListUi(
                uiState.blocks,
                onBlockClick,
                modifier = Modifier.weight(.5f)
            )
            RecentTransactionsUi(
                modifier = Modifier.weight(.5f)
            )
        }
    }
}

@Composable
private fun RecentTransactionsUi(
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Text(text = "Recent Transactions")
    }
}

@Composable
private fun BlocksListUi(
    blocks: List<Block>,
    onBlockClick: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberLazyListState()
    LazyColumn(
        userScrollEnabled = true,
        state = scrollState,
        modifier = modifier
    ) {
        item { Text(text = "Last ${blocks.size} OnChain Blocks") }
        items(
            items = blocks,
            key = { it.id },
        ) {
            BlockCardUi(
                modifier = Modifier.clickable { onBlockClick(it.id) },
                id = it.id,
                time = it.timestamp.formatTimestamp(),
                size = it.bits.asMegabytes(),
                transactions = it.txCount.toString(),
            )
        }
    }
}

@Composable
private fun BlockCardUi(
    modifier: Modifier = Modifier,
    time: String = "",
    size: String = "",
    transactions: String = "",
    id: String = "",
) {
    Card(
        shape = CardDefaults.elevatedShape,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        InfoRow(
            label = id.trimId()
        )
        Row {
            InfoRow(
                label = "At:",
                value = time,
            )
            InfoRow(label = size)
            InfoRow(
                label = transactions,
                value = "transactions",
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    BitlabTheme {
        val uiState = HomeViewModel.UiState(
//            blocks = List(3) {
//                Block(
//                    id = "$it",
//                    timestamp = System.currentTimeMillis(),
//                    bits = it * 100000L,
//                    txCount = it * 100,
//                )
//            }
        )

        HomeScreen(uiState)
    }
}