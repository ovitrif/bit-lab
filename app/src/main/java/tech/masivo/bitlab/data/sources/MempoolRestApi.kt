package tech.masivo.bitlab.data.sources

import retrofit2.http.GET
import retrofit2.http.Path
import tech.masivo.bitlab.data.model.TransactionResult

interface MempoolRestApi {
    @GET("block/{id}/txs/0")
    suspend fun getBlockTransactions(@Path("id") blockId: String): List<TransactionResult>
}
