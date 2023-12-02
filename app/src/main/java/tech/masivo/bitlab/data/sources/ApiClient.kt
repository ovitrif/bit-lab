package tech.masivo.bitlab.data.sources

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import tech.masivo.bitlab.data.DataModuleFakeInjector
import javax.inject.Inject

@OptIn(ExperimentalSerializationApi::class)
class ApiClient @Inject constructor() {
    private val okHttpClient: OkHttpClient = DataModuleFakeInjector.okHttpClient

    private val mempoolBaseUrl = "https://mempool.space/api/"
    private val contentType = "application/json".toMediaType()

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private fun retrofitBuilder(baseUrl: String) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    val mempool: MempoolApi = retrofitBuilder(mempoolBaseUrl).create(MempoolApi::class.java)
}