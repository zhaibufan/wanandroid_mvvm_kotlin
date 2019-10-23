package com.zhai.wanandroid_mvvm.base

import com.zhai.wanandroid_mvvm.model.bean.WanResponse

open class BaseRepository {

    suspend fun <T : Any> apiCall(call : suspend () -> WanResponse<T>) : WanResponse<T> {
        return call.invoke()
    }
}