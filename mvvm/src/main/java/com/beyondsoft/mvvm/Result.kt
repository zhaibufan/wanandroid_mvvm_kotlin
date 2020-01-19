package com.beyondsoft.mvvm

/**
 * 密封类  所定义的子类都必须继承于密封类，表示一组受限的类
 */
sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data : T): Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}