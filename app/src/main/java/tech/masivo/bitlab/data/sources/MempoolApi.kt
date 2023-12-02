package tech.masivo.bitlab.data.sources

import retrofit2.http.GET
import retrofit2.http.Path
import tech.masivo.bitlab.data.model.TransactionResult

interface MempoolApi {
    @GET("block/{id}/txs")
    suspend fun getBlockTransactions(@Path("id") blockId: String): List<TransactionResult>
}