package com.zhai.wanandroid_mvvm.model.api

import com.zhai.wanandroid_mvvm.model.bean.ArticleList
import com.zhai.wanandroid_mvvm.model.bean.Banner
import com.zhai.wanandroid_mvvm.model.bean.User
import com.zhai.wanandroid_mvvm.model.bean.WanResponse
import retrofit2.http.*

interface WanServer {

    @GET("/article/list/{page}/json")
    suspend fun getHomeArticles(@Path("page") page: Int): WanResponse<ArticleList>

    @GET("/banner/json")
    suspend fun getBanner(): WanResponse<List<Banner>>

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(@Field("username") userName: String, @Field("password") passWord: String): WanResponse<User>

    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(@Field("username") userName: String, @Field("password") passWord: String, @Field("repassword") rePassWord: String): WanResponse<User>
}