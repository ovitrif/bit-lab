@file:Suppress("SpellCheckingInspection")

package tech.masivo.bitlab.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MempoolResult(
    val transactions: List<Transaction>,
    val blocks: List<Block>? = null,
)

@Serializable
data class Transaction(
    val txid: String,
    val rate: Double,
)

@Serializable
data class Block(
    val id: String,
    val timestamp: Long,
    val size: Long,
    @SerialName("tx_count")
    val txCount: Long,
)
