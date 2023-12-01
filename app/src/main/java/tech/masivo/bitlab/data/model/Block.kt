package tech.masivo.bitlab.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BlockResult(
    val id: String? = null,
    val timestamp: Int? = null,
    val height: Int? = null,
    val version: Int? = null,
    val bits: Int? = null,
    val nonce: Long? = null,
    val difficulty: Double? = null,
    val merkleRoot: String? = null,
    val txCount: Int? = null,
    val size: Int? = null,
    val weight: Int? = null,
    val previousblockhash: String? = null,
    val extras: BlockExtrasResult? = BlockExtrasResult()
)

@Serializable
data class BlockExtrasResult(
    val coinbaseRaw: String? = null,
    val medianFee: Int? = null,
    val feeRange: ArrayList<Int> = arrayListOf(),
    val reward: Int? = null,
    val totalFees: Int? = null,
    val avgFee: Int? = null,
    val avgFeeRate: Int? = null,
    val pool: BlockPoolResult? = BlockPoolResult()
)

@Serializable
data class BlockPoolResult(
    var id: Int? = null,
    var name: String? = null,
    var slug: String? = null
)
