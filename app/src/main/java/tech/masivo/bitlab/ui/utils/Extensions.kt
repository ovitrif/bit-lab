package tech.masivo.bitlab.ui.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val BLOCK_DATE_FORMAT = SimpleDateFormat("HH:mm:ss", Locale.ROOT)

fun Long.formatTimestamp(): String {
    return BLOCK_DATE_FORMAT.format(Date(this))
}

fun Long.asMegabytes(): String {
    val megabytes = this.toDouble() / 8 / 1024 / 102
    return String.format("%.2f MB", megabytes)
}

fun String.trimId(): String = this.takeLast(8)

/**
 * Converts sat to btc (1 BTC = 100,000,000 sat)
 * TODO: use BigDecimal to avoid rounding errors
 */
fun Long.satToBtc(): Double = this.toDouble() / 100_000_000

/**
 * Formats to 1 decimal place if it has decimals, otherwise it will be an integer
 */
fun Double.formatRate(): String {
    val decimals = if (this % 1.0 == 0.0) 0 else 1
    return "%.${decimals}f".format(this)
}
