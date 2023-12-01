package tech.masivo.bitlab.ui.utils

import java.text.SimpleDateFormat
import java.util.*

private val BLOCK_DATE_FORMAT = SimpleDateFormat("HH:mm:ss", Locale.ROOT)

fun Long.formatTimestamp(): String {
    return BLOCK_DATE_FORMAT.format(Date(this))
}