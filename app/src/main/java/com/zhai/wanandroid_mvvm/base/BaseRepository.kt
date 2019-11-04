package com.zhai.wanandroid_mvvm.base

import com.zhai.wanandroid_mvvm.model.bean.Result
import com.zhai.wanandroid_mvvm.model.bean.WanResponse
import java.io.IOException
import java.lang.Exception

open class BaseRepository {

    suspend fun <T : Any> apiCall(call: suspend () -> WanResponse<T>): WanResponse<T> {
        return call.invoke()
    }

    suspend fun <T : Any> safeApiCall(
        call: suspend () -> Result<T>,
        errorMessage: String
    ): Result<T> {
        return try {
            call()
        } catch (e: Exception) {
            Result.Error(IOException(errorMessage, e))
        }
    }
}