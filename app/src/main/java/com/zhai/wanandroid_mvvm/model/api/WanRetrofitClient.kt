package com.zhai.wanandroid_mvvm.model.api

import com.zhai.wanandroid_mvvm.App
import com.zhai.wanandroid_mvvm.Constants
import com.zhai.wanandroid_mvvm.base.BaseRetrofitClient
import com.zhai.wanandroid_mvvm.utils.NetWorkUtils
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object WanRetrofitClient : BaseRetrofitClient() {

    val wServer by lazy { getServer(WanServer::class.java, Constants.BASE_URL) }

    override fun handleConverterFactory(builder: Retrofit.Builder) {
        builder.addConverterFactory(GsonConverterFactory.create())
    }

    override fun handleBuilder(builder: OkHttpClient.Builder) {
        super.handleBuilder(builder)
        val httpCacheDirectory = File(App.CONTEXT.cacheDir, "responses")
        val cacheSize = 10 * 1024 * 1024L // 10 MiB
        val cache = Cache(httpCacheDirectory, cacheSize)
        builder.cache(cache)
            .addInterceptor { chain ->
                var request = chain.request()
                if (!NetWorkUtils.isNetworkAvailable(App.CONTEXT)) {
                    request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
                }
                val response = chain.proceed(request)
                if (!NetWorkUtils.isNetworkAvailable(App.CONTEXT)) {
                    val maxAge = 60 * 60
                    response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=$maxAge")
                        .build()
                } else {
                    val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
                    response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .build()
                }

                response
            }
    }
}