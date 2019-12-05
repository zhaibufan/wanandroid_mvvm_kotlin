package com.zhai.wanandroid_mvvm.model.repository

import com.zhai.wanandroid_mvvm.base.BaseRepository
import com.zhai.wanandroid_mvvm.model.api.WanRetrofitClient
import com.zhai.wanandroid_mvvm.model.bean.ArticleList
import com.zhai.wanandroid_mvvm.model.bean.Hot
import com.zhai.wanandroid_mvvm.model.bean.WanResponse

class SearchRepository : BaseRepository() {

    suspend fun searchHot(page : Int, key : String) : WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.wServer.searchHot(page, key) }
    }

    suspend fun getWebSites() : WanResponse<List<Hot>> {
        return apiCall { WanRetrofitClient.wServer.getWebsites() }
    }

    suspend fun getHotSearch(): WanResponse<List<Hot>> {
        return apiCall { WanRetrofitClient.wServer.getHot() }
    }

    suspend fun collectArticle(articleId: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.wServer.collectArticle(articleId) }
    }

    suspend fun unCollectArticle(articleId: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.wServer.cancelCollectArticle(articleId) }
    }
}