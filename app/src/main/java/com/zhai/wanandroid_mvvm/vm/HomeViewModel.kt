package com.zhai.wanandroid_mvvm.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.zhai.wanandroid_mvvm.base.BaseViewModel
import com.zhai.wanandroid_mvvm.executeResponse
import com.zhai.wanandroid_mvvm.model.bean.ArticleList
import com.zhai.wanandroid_mvvm.model.bean.Banner
import com.zhai.wanandroid_mvvm.model.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeViewModel : BaseViewModel() {

    private val homeRepository by lazy { HomeRepository() }
    val articleData : MutableLiveData<ArticleList> = MutableLiveData()

    val mBanner : LiveData<List<Banner>> = liveData {
        kotlin.runCatching {
            val data = withContext(Dispatchers.IO) { homeRepository.getHomeBanners() }
            emit(data.data)
        }
    }

    fun getHomeArticles(pageIndex : Int) {
        launch {
            val result = withContext(Dispatchers.IO) {homeRepository.getHomeArticles(pageIndex)}
            executeResponse(result, {articleData.value = result.data}, {})
        }
    }
}