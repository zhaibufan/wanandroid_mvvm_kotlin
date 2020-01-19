package com.beyondsoft.mvvm


/**
 * 数据类
 */
data class WanResponse<out T>(val errorCode : Int, val errorMsg : String, val data : T)
