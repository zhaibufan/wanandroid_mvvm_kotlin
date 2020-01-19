package com.beyondsoft.mvvm

import android.app.Activity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException

suspend fun executeResponse(
    result: WanResponse<Any>,
    successBlock: suspend CoroutineScope.() -> Unit,
    errorBlock: suspend CoroutineScope.() -> Unit
) {
    coroutineScope {
        if (result.errorCode == -1) {
            errorBlock()
        } else {
            successBlock()
        }
    }
}

fun Activity.onNetError(e: Throwable, func: (e: Throwable) -> Unit) {
    if (e is HttpException) {
        func(e)
    }
}

fun WanResponse<Any>.isSuccess(): Boolean = this.errorCode == 0