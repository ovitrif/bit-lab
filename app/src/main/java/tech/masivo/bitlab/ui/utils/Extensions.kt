package tech.masivo.bitlab.ui.utils

import java.text.SimpleDateFormat
import java.util.*

private val BLOCK_DATE_FORMAT = SimpleDateFormat("HH:mm:ss", Locale.ROOT)

fun Long.formatTimestamp(): String {
    return BLOCK_DATE_FORMAT.format(Date(this))
}

fun Long.asMegabytes(): String {
    val megabytes = this.toDouble() / 8 / 1024 / 102
    return String.format("%.2f MB", megabytes)
}

fun String.trimId(): String = this.takeLast(8)