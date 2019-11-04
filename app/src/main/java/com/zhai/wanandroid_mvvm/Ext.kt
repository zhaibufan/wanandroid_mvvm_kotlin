package com.zhai.wanandroid_mvvm

import com.zhai.wanandroid_mvvm.model.bean.WanResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

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