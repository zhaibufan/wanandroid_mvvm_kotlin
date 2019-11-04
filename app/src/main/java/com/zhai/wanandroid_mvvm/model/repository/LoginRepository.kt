package com.zhai.wanandroid_mvvm.model.repository

import com.zhai.wanandroid_mvvm.base.BaseRepository
import com.zhai.wanandroid_mvvm.model.api.WanRetrofitClient
import com.zhai.wanandroid_mvvm.model.bean.Result
import com.zhai.wanandroid_mvvm.model.bean.User
import java.io.IOException

class LoginRepository : BaseRepository() {

    suspend fun login(userName : String, pwd : String) : Result<User> {
        return safeApiCall({requestLogin(userName, pwd)}, "登录失败")
    }

    private suspend fun requestLogin(userName : String, pwd : String) : Result<User> {
        val response = WanRetrofitClient.wServer.login(userName, pwd)
        return if (response.errorCode != -1) {
            Result.Success(response.data)
        } else {
            Result.Error(IOException(response.errorMsg))
        }
    }

    suspend fun register(userName: String, pwd: String) : Result<User> {
        return safeApiCall({requestRegister(userName, pwd)}, "注册失败")
    }

    private suspend fun requestRegister(userName: String, pwd: String) : Result<User> {
        val response = WanRetrofitClient.wServer.register(userName, pwd, pwd)
        return if (response.errorCode != -1) Result.Success(response.data) else Result.Error(IOException(response.errorMsg))
    }
}