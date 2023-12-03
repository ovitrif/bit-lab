@file:Suppress("SpellCheckingInspection")

package tech.masivo.bitlab.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionResult(
    val txid: String,
    val vin: List<VinResult>,
    val vout: List<VoutResult>,
    val fee: Long,
)

@Serializable
data class VinResult(
    val txid: String,
    val vout: Long,
    val prevout: VoutResult? = null,
)

@Serializable
data class VoutResult(
    @SerialName("scriptpubkey_address")
    val scriptpubkeyAddress: String? = null,
    val value: Long,
)
