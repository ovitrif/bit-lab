package tech.masivo.bitlab.data.sources

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject

class RestApiClient @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val json: Json,
) {
    private val mempoolBaseUrl = "https://mempool.space/api/"
    private val contentType = "application/json".toMediaType()

    @OptIn(ExperimentalSerializationApi::class)
    private fun retrofitBuilder(baseUrl: String) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    val mempool: MempoolRestApi = retrofitBuilder(mempoolBaseUrl).create(MempoolRestApi::class.java)
}