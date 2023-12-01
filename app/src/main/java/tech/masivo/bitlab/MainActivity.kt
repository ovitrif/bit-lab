package tech.masivo.bitlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import tech.masivo.bitlab.ui.theme.BitlabTheme

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
            modifier = modifier
        )
        Button(onClick = uiState.getBlocks) {
            Text(text = "Get Blocks")
        }
        uiState.blocks.forEach {
            ListItem(it.toString())
        }
        ListItem()
    }
}

@Composable
fun ListItem(text: String = "") {
    Text(text = text)
    Divider()
}

@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Composable
fun AppUiPreview() {
    BitlabTheme {
        val uiState = MainViewModel.UiState()
        AppUi(uiState)
    }
}