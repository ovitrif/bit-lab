@file:Suppress("SpellCheckingInspection")

package tech.masivo.bitlab.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MempoolResult(
    val mempoolInfo: MempoolInfo,
    val vBytesPerSecond: Long,
    @SerialName("mempool-blocks")
    val mempoolBlocks: List<Block>,
    val transactions: List<Transaction>,
    val fees: Fees,
    val blocks: List<Block2>? = null,
    val conversions: Conversions? = null,
    val da: Da,
    val rbfSummary: List<RbfSummary>? = null,
)

@Serializable
data class MempoolInfo(
    val loaded: Boolean,
    val size: Long,
    val bytes: Long,
    val usage: Long,
    @SerialName("total_fee")
    val totalFee: Double,
    val maxmempool: Long,
    val mempoolminfee: Double,
    val minrelaytxfee: Double,
    val incrementalrelayfee: Double,
    val unbroadcastcount: Long,
    val fullrbf: Boolean,
)

@Serializable
data class Block(
    val blockSize: Long,
    val blockVSize: Double,
    val nTx: Long,
    val totalFees: Long,
    val medianFee: Double,
    val feeRange: List<Double>,
)

@Serializable
data class Transaction(
    val txid: String,
    val fee: Long,
    val vsize: Double,
    val value: Long,
    val rate: Double,
)

@Serializable
data class Fees(
    val fastestFee: Long,
    val halfHourFee: Long,
    val hourFee: Long,
    val economyFee: Long,
    val minimumFee: Long,
)

@Serializable
data class Block2(
    val id: String,
    val height: Long,
    val version: Long,
    val timestamp: Long,
    val bits: Long,
    val nonce: Long,
    val difficulty: Double,
    @SerialName("merkle_root")
    val merkleRoot: String,
    @SerialName("tx_count")
    val txCount: Long,
    val size: Long,
    val weight: Long,
    val previousblockhash: String,
    val mediantime: Long,
    val stale: Boolean,
    val extras: Extras,
)

@Serializable
data class Extras(
    val reward: Long,
    val coinbaseRaw: String,
    val medianFee: Double,
    val feeRange: List<Double>,
    val totalFees: Long,
    val avgFee: Long,
    val avgFeeRate: Long,
    val utxoSetChange: Long,
    val avgTxSize: Double,
    val totalInputs: Long,
    val totalOutputs: Long,
    val totalOutputAmt: Long,
    val segwitTotalTxs: Long,
    val segwitTotalSize: Long,
    val segwitTotalWeight: Long,
    val virtualSize: Double,
    val coinbaseAddress: String,
    val coinbaseSignature: String,
    val coinbaseSignatureAscii: String,
    val header: String,
    val pool: Pool,
    val matchRate: Double,
    val expectedFees: Long,
    val expectedWeight: Long,
    val similarity: Double,
)

@Serializable
data class Pool(
    val id: Long,
    val name: String,
    val slug: String,
)

@Serializable
data class Conversions(
    val time: Long,
    @SerialName("USD")
    val usd: Long,
    @SerialName("EUR")
    val eur: Long,
    @SerialName("GBP")
    val gbp: Long,
    @SerialName("CAD")
    val cad: Long,
    @SerialName("CHF")
    val chf: Long,
    @SerialName("AUD")
    val aud: Long,
    @SerialName("JPY")
    val jpy: Long,
)

@Serializable
data class Da(
    val progressPercent: Double,
    val difficultyChange: Double,
    val estimatedRetargetDate: Long,
    val remainingBlocks: Long,
    val remainingTime: Long,
    val previousRetarget: Double,
    val previousTime: Long,
    val nextRetargetHeight: Long,
    val timeAvg: Long,
    val timeOffset: Long,
    val expectedBlocks: Double,
)

@Serializable
data class RbfSummary(
    val txid: String,
    val mined: Boolean,
    val fullRbf: Boolean,
    val oldFee: Long,
    val oldVsize: Double,
    val newFee: Long,
    val newVsize: Double,
)
