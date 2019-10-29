package com.zhai.wanandroid_mvvm.model.api

import com.zhai.wanandroid_mvvm.model.bean.ArticleList
import com.zhai.wanandroid_mvvm.model.bean.Banner
import com.zhai.wanandroid_mvvm.model.bean.WanResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface WanServer {

    @GET("/article/list/{page}/json")
    suspend fun getHomeArticles(@Path("page") page: Int): WanResponse<ArticleList>

    @GET("/banner/json")
    suspend fun getBanner(): WanResponse<List<Banner>>
}