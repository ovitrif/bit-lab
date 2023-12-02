package tech.masivo.bitlab.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionResult(
    val txid: String,
    val version: Long = 0,
    val locktime: Long = 0,
    val size: Long = 0,
    val weight: Long = 0,
    val fee: Long = 0,
    val status: TransactionStatusResult,
)

@Serializable
data class TransactionStatusResult(
    val confirmed: Boolean = false,
    @SerialName("block_height")
    val blockHeight: Long = 0,
    @SerialName("block_hash")
    val blockHash: String = "",
    @SerialName("block_time")
    val blockTime: Long = 0,
)
