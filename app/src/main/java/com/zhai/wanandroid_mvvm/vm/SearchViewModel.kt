package com.zhai.wanandroid_mvvm.vm

import androidx.lifecycle.MutableLiveData
import com.beyondsoft.mvvm.base.BaseViewModel
import com.beyondsoft.mvvm.executeResponse
import com.zhai.wanandroid_mvvm.model.bean.ArticleList
import com.zhai.wanandroid_mvvm.model.bean.Hot
import com.zhai.wanandroid_mvvm.model.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchViewModel : BaseViewModel() {

    private val mRepository by lazy { SearchRepository() }
    val mArticleList: MutableLiveData<ArticleList> = MutableLiveData()
    val mWebSiteHot: MutableLiveData<List<Hot>> = MutableLiveData()
    val mHotSearch: MutableLiveData<List<Hot>> = MutableLiveData()

    fun searchHot(page: Int, key: String) {
        launch {
            val result = withContext(Dispatchers.IO) { mRepository.searchHot(page, key) }
            executeResponse(result, { mArticleList.value = result.data }, {})
        }
    }

    fun getWebSites() {
        launch {
            val result = withContext(Dispatchers.IO) {mRepository.getWebSites()}
            executeResponse(result, {mWebSiteHot.value = result.data}, {})
        }
    }

    fun getHotSearch() {
        launch {
            val result = withContext(Dispatchers.IO) { mRepository.getHotSearch() }
            executeResponse(result, { mHotSearch.value = result.data }, {})
        }
    }

    fun collectArticle(articleId : Int, isCollect : Boolean) {
        launch {
            withContext(Dispatchers.IO) {
                if (isCollect) {
                    mRepository.collectArticle(articleId)
                } else {
                    mRepository.unCollectArticle(articleId)
                }
            }
        }
    }
}