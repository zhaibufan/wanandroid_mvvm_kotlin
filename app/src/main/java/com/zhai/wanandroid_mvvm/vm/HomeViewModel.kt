package com.zhai.wanandroid_mvvm.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.beyondsoft.mvvm.base.BaseViewModel
import com.beyondsoft.mvvm.executeResponse
import com.zhai.wanandroid_mvvm.model.bean.ArticleList
import com.zhai.wanandroid_mvvm.model.bean.Banner
import com.zhai.wanandroid_mvvm.model.repository.HomeRepository
import kotlinx.coroutines.*

class HomeViewModel : BaseViewModel() {

    private val homeRepository by lazy { HomeRepository() }
    val articleData: MutableLiveData<ArticleList> = MutableLiveData()

    val mBanner: LiveData<List<Banner>> = liveData {
        kotlin.runCatching {
            val data = withContext(Dispatchers.IO) { homeRepository.getHomeBanners() }
            emit(data.data)
        }
    }

    fun getHomeArticles(pageIndex: Int, errorBlock: suspend CoroutineScope.() -> Unit) {
        launch {
            val result = withContext(Dispatchers.IO) {
                homeRepository.getHomeArticles(pageIndex)
            }

            executeResponse(result, { articleData.value = result.data }, errorBlock)

            //这种方式也可以，executeResponse只是将这个逻辑抽取和封装成了一个方法，每个接口都可以调用
            //这种方式下coroutineScope可写可不写，因为launch方法中已经调用了coroutineScope方法
//            if (result.errorCode == -1) {
//                errorBlock()
//            } else {
//                articleData.value = result.data
//            }
        }
    }
}