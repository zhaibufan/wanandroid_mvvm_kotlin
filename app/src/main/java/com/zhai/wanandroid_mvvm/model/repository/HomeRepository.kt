package com.zhai.wanandroid_mvvm.model.repository

import com.beyondsoft.mvvm.WanResponse
import com.beyondsoft.mvvm.base.BaseRepository
import com.zhai.wanandroid_mvvm.model.api.WanRetrofitClient
import com.zhai.wanandroid_mvvm.model.bean.ArticleList
import com.zhai.wanandroid_mvvm.model.bean.Banner

class HomeRepository : BaseRepository() {

    suspend fun getHomeArticles(pageIndex : Int): WanResponse<ArticleList> {
        return apiCall {WanRetrofitClient.wServer.getHomeArticles(pageIndex)}
    }

    suspend fun getHomeBanners() : WanResponse<List<Banner>> {
        return apiCall { WanRetrofitClient.wServer.getBanner() }
    }
}