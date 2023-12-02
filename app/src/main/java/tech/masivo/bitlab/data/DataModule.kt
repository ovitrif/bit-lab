package tech.masivo.bitlab.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

// TODO: Debug Hilt build error and refactor to Hilt module, then remove DataModuleFakeInjector
object DataModuleFakeInjector {
    val okHttpClient by lazy {
        DataModule.provideOkHttpClient()
    }
}

//@Module
//@InstallIn(SingletonComponent::class)
object DataModule {

//    @Provides
//    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(39, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                })
            .build()
    }
}
