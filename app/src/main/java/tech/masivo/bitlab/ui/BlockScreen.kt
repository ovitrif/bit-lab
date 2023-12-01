package tech.masivo.bitlab.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import tech.masivo.bitlab.ui.theme.BitlabTheme

@Composable
fun BlockScreen(
    id: String,
) {
    Text(text = "Block id: $id")
}


@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Composable
private fun BlockScreenPreview() {
    BitlabTheme {
        BlockScreen("blockId")
    }
}