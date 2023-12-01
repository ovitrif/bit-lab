package tech.masivo.bitlab.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Inject

@Suppress("unused")
class AppHttpClient @Inject constructor() {
    private val baseUrl = "https://mempool.space/docs/api"
    private val client = HttpClient(Android) {
        expectSuccess = true
        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.INFO
        }
        install(ContentNegotiation) {
            json()
        }
        install(HttpTimeout) {
            val timeout = 30000L
            connectTimeoutMillis = timeout
            requestTimeoutMillis = timeout
            socketTimeoutMillis = timeout
        }
        defaultRequest {
            url(baseUrl)
        }
    }
}