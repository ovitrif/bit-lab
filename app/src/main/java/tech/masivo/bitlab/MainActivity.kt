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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import tech.masivo.bitlab.data.model.BlockResult
import tech.masivo.bitlab.ui.theme.BitlabTheme
import tech.masivo.bitlab.ui.utils.asMegabytes
import tech.masivo.bitlab.ui.utils.formatTimestamp

object Routes {
    const val Home = "home"
    const val Block = "block"
}

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

                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Routes.Home) {
                        composable(Routes.Home) {
                            HomeScreen(
                                uiState = uiState.value,
                                onNavigateToBlock = {
                                    navController.navigate("${Routes.Block}/id=$it")
                                }
                            )
                        }
                        composable("${Routes.Block}/id={id}") { backStackEntry ->
                            backStackEntry.arguments?.getString("id")?.let {
                                BlockScreen(it)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BlockScreen(
    id: String,
) {
    Text(text = "Block id: $id")
}


@Composable
fun HomeScreen(
    uiState: MainViewModel.UiState,
    modifier: Modifier = Modifier,
    onNavigateToBlock: (id: String) -> Unit = {},
) {
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
            onBlockClick = onNavigateToBlock,
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
                time = it.timestamp.formatTimestamp(),
                size = it.bits.asMegabytes(),
                transactions = it.txCount.toString(),
            )
        }
    }
}

@Composable
fun BlockCardUi(
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

@Composable
private fun InfoRow(
    label: String = "",
    value: String = "",
) {
    Row(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Black)
        )
        Text(text = value, style = MaterialTheme.typography.labelLarge)
    }
}


@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    BitlabTheme {
        val uiState = MainViewModel.UiState(
            blocks = List(3) {
                BlockResult(
                    id = "$it",
                    timestamp = System.currentTimeMillis(),
                    bits = it * 100000L,
                    txCount = it * 100,
                )
            }
        )

        HomeScreen(uiState)
    }
}