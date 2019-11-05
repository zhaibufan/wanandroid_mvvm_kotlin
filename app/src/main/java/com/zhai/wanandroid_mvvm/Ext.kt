package com.zhai.wanandroid_mvvm

import android.app.Activity
import com.zhai.wanandroid_mvvm.model.bean.WanResponse
import com.zhai.wanandroid_mvvm.utils.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException
import java.lang.Exception

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
        toast("网络异常")
        func(e)
    }
}

fun WanResponse<Any>.isSuccess(): Boolean = this.errorCode == 0