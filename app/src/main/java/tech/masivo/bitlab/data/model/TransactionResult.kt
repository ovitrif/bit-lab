@file:Suppress("SpellCheckingInspection")

package tech.masivo.bitlab.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionResult(
    val txid: String,
    val version: Long = 0,
    val locktime: Long = 0,
    val vin: List<VinResult>,
    val vout: List<VoutResult>,
    val size: Long = 0,
    val weight: Long = 0,
    val sigops: Long,
    val fee: Long = 0,
)

@Serializable
data class VinResult(
    val txid: String,
    val vout: Long,
    val prevout: VoutResult,
    val scriptsig: String,
    @SerialName("scriptsig_asm")
    val scriptsigAsm: String,
    val witness: List<String>,
    @SerialName("is_coinbase")
    val isCoinbase: Boolean,
    val sequence: Long,
    @SerialName("inner_witnessscript_asm")
    val innerWitnessscriptAsm: String,
)

@Serializable
data class VoutResult(
    val scriptpubkey: String,
    @SerialName("scriptpubkey_asm")
    val scriptpubkeyAsm: String,
    @SerialName("scriptpubkey_type")
    val scriptpubkeyType: String,
    @SerialName("scriptpubkey_address")
    val scriptpubkeyAddress: String,
    val value: Long,
)
