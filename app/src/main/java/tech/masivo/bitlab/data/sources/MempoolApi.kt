package tech.masivo.bitlab.data.sources

import retrofit2.http.GET
import retrofit2.http.Path
import tech.masivo.bitlab.data.model.BlockResult
import tech.masivo.bitlab.data.model.TransactionResult

interface MempoolApi {
    // TODO: revisit start_index path param
    @GET("block/{id}/txs")
    suspend fun getBlockTransactions(
        @Path("id") blockId: String,
    ): List<TransactionResult>
}