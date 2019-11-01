package com.zhai.wanandroid_mvvm.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

open class BaseViewModel : ViewModel(), LifecycleObserver {

    //所有接口通用的异常LiveData数据类,mExceptions数据在基类中监听，重写onError
    val mExceptions: MutableLiveData<Throwable> = MutableLiveData()

    fun launch(runBlock: suspend CoroutineScope.() -> Unit) {
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

        // coroutineScope的作用是当耗时任务执行完成后自动切换到协程的初始线程
        // 协程初始线程就是上面viewModelScope.launch的线程
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