package tech.masivo.bitlab.data.sources

import retrofit2.http.GET
import retrofit2.http.Path
import tech.masivo.bitlab.data.model.BlockResult
import tech.masivo.bitlab.data.model.TransactionResult

interface MempoolApi {
    // TODO: handle startHeight
    @GET("v1/blocks/{startHeight}")
    suspend fun getBlocks(@Path("startHeight") startHeight: String = "730000"): List<BlockResult>

    // TODO: use start_index
    @GET("block/{id}/txs")
    suspend fun getBlockTransactions(
        @Path("id") blockId: String,
    ): List<TransactionResult>
}