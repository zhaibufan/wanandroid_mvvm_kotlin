package com.zhai.wanandroid_mvvm.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel(), LifecycleObserver {

    //所有接口通用的异常LiveData数据类
    val mExceptions: MutableLiveData<Throwable> = MutableLiveData()

    fun launch(runBlock: suspend CoroutineScope.() -> Unit) {
//        viewModelScope.launch {
//            tryCatch(runBlock, {}, {}, true)
//        }

        launchOnUI {
            tryCatch(runBlock, {}, {}, true)
        }
    }

    //viewModelScope.launch默认是在主线程
    private fun launchOnUI(runBlock: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            runBlock()
        }
    }

    private suspend fun tryCatch(
        runBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit,
        handleCancellationExceptionManually: Boolean = false
    ) {

        coroutineScope {
            try {
                runBlock()
            } catch (e: Throwable) {
                if (e !is CancellationException || handleCancellationExceptionManually) {
                    mExceptions.value = e
                    catchBlock(e)
                } else {
                    throw e
                }
            } finally {
                finallyBlock()
            }
        }
    }

}