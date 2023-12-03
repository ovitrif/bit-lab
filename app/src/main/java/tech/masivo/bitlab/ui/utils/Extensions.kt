package tech.masivo.bitlab.ui.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val BLOCK_DATE_FORMAT = SimpleDateFormat("HH:mm:ss", Locale.ROOT)

/**
 * @return A string representation of the unix timestamp in the "HH:mm:ss" format.
 */
fun Long.formatTimestamp(): String {
    return BLOCK_DATE_FORMAT.format(Date(this))
}

/**
 * @return A string representation of the megabytes with 2 decimal places.
 */
fun Long.asMegabytes(): String {
    return "%.2f MB".format(this.toDouble() / 8 / 1024 / 102)
}

/**
 * @return A string containing the last 8 characters of the original.
 */
fun String.trimId(): String = this.takeLast(8)

/**
 * Converts sat to btc (1 BTC = 100,000,000 sat)
 * TODO: use BigDecimal to avoid rounding errors
 */

/**
 * Converts a value representing satoshis into a Double representation of bitcoins.
 *
 * @return A double representation of the bitcoins.
 */
fun Long.satToBtc(): Double = this.toDouble() / 100_000_000

/**
 * Formats a value representing a fee rate in sat/vB into a string with maximum 1 decimal place.
 *
 * @return A string representation of the double value.
 */
fun Double.formatRate(): String = DecimalFormat("#.#").format(this)
