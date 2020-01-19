package com.beyondsoft.mvvm.base

import com.beyondsoft.mvvm.WanResponse
import java.io.IOException
import java.lang.Exception
import com.beyondsoft.mvvm.Result

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