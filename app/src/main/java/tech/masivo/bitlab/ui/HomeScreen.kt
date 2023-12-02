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
import tech.masivo.bitlab.data.model.Block
import tech.masivo.bitlab.ui.components.InfoRow
import tech.masivo.bitlab.ui.theme.BitlabTheme
import tech.masivo.bitlab.ui.utils.asMegabytes
import tech.masivo.bitlab.ui.utils.formatTimestamp

@Composable
fun HomeScreen(
    uiState: HomeViewModel.UiState,
    onNavigateToBlock: (id: String) -> Unit = {},
) {
    Column {
        Text(
            text = uiState.title,
            style = MaterialTheme.typography.titleLarge,
        )
        BlocksListUi(
            uiState.blocks,
            onBlockClick = onNavigateToBlock,
        )
        BlockCardUi()
    }
}

@Composable
private fun BlocksListUi(
    blocks: List<Block>,
    onBlockClick: (id: String) -> Unit,
) {
    val scrollState = rememberLazyListState()
    LazyColumn(
        userScrollEnabled = true,
        state = scrollState,
        modifier = Modifier.fillMaxHeight()
    ) {
        items(
            items = blocks,
            key = { it.id },
        ) {
            BlockCardUi(
                modifier = Modifier.clickable { onBlockClick(it.id) },
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
) {
    Card(
        shape = CardDefaults.elevatedShape,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        InfoRow(
            label = "Time:",
            value = time,
        )
        InfoRow(
            label = "Size:",
            value = size,
        )
        InfoRow(
            label = "Transactions:",
            value = transactions,
        )
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