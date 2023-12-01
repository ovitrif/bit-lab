package tech.masivo.bitlab.data.sources

import retrofit2.http.GET
import retrofit2.http.Path
import tech.masivo.bitlab.data.model.BlockResult

interface BlocksApi {

    @GET("blocks/{startHeight}")
    suspend fun getBlocks(@Path("startHeight") startHeight: String = "730000"): List<BlockResult>
}