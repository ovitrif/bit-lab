package tech.masivo.bitlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import tech.masivo.bitlab.data.model.BlockResult
import tech.masivo.bitlab.ui.theme.BitlabTheme
import tech.masivo.bitlab.ui.utils.formatTimestamp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BitlabTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
                    AppUi(uiState.value)
                }
            }
        }
    }
}

@Composable
fun AppUi(uiState: MainViewModel.UiState, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = uiState.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier
        )
        Button(onClick = uiState.getBlocks) {
            Text(text = "Get Blocks")
        }
        BlocksListUi(
            uiState.blocks,
            onBlockClick = uiState.onBlockClick,
        )
        BlockCardUi()
    }
}

@Composable
fun BlocksListUi(
    blocks: List<BlockResult>,
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
                time = it.timestamp.formatTimestamp()
            )
        }
    }
}

@Composable
fun BlockCardUi(
    modifier: Modifier = Modifier,
    time: String = "",
) {
    Card(
        shape = CardDefaults.elevatedShape,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Outlined.DateRange, contentDescription = null)
            Text(text = time, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Composable
fun AppUiPreview() {
    BitlabTheme {
        val uiState = MainViewModel.UiState(
            blocks = List(3) {
                BlockResult(
                    id = "$it",
                    timestamp = System.currentTimeMillis(),
                )
            }
        )

        AppUi(uiState)
    }
}