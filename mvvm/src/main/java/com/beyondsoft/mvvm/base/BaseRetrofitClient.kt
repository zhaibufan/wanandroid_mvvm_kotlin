package com.beyondsoft.mvvm.base

import com.beyondsoft.mvvm.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

abstract class BaseRetrofitClient {

    companion object {
        const val TIME_OUT = 5
    }

    private val client: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            val okHttpLoggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                okHttpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            } else {
                okHttpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            }
            builder.connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                .addInterceptor(okHttpLoggingInterceptor)

            handleBuilder(builder)

            return builder.build()
        }

    open fun handleBuilder(builder: OkHttpClient.Builder) {}

    fun <T> getServer(cla: Class<T>, baseUrl: String): T {
        val builder = Retrofit.Builder()
        handleConverterFactory(builder)
        return builder.client(client)
            .baseUrl(baseUrl)
            .build()
            .create(cla)
    }

    abstract fun handleConverterFactory(builder: Retrofit.Builder)
}