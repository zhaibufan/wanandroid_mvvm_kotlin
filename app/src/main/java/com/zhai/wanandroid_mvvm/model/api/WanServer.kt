package com.zhai.wanandroid_mvvm.model.api

import com.beyondsoft.mvvm.WanResponse
import com.zhai.wanandroid_mvvm.model.bean.*
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
    suspend fun register(
        @Field("username") userName: String, @Field("password") passWord: String, @Field(
            "repassword"
        ) rePassWord: String
    ): WanResponse<User>

    @FormUrlEncoded
    @POST("/article/query/{page}/json")
    suspend fun searchHot(@Path("page") page: Int, @Field("k") key: String): WanResponse<ArticleList>

    @GET("/friend/json")
    suspend fun getWebsites() : WanResponse<List<Hot>>

    @GET("/hotkey/json")
    suspend fun getHot(): WanResponse<List<Hot>>

    @POST("/lg/collect/{id}/json")
    suspend fun collectArticle(@Path("id") id : Int) : WanResponse<ArticleList>

    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun cancelCollectArticle(@Path("id") id: Int): WanResponse<ArticleList>
}