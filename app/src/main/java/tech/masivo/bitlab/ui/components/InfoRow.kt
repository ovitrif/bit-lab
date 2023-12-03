package tech.masivo.bitlab.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

/**
 * A row of information with a label and a value.
 * If any of the parameters is null, the corresponding side of the row will be empty.
 *
 * @param label The label to be displayed on the left side of the row (optional).
 * @param value The value to be displayed on the right side of the row (optional).
*/
@Composable
fun InfoRow(
    modifier: Modifier = Modifier,
    label: String? = null,
    value: String? = null,
) {
    Row(
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        label?.let {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Black)
            )
        }
        value?.let {
            Text(
                text = value,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}