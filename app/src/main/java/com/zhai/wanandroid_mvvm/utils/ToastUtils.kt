package com.zhai.wanandroid_mvvm.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.toast(content : String, duration:Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, content, duration).apply { show() }
}

fun Context.toast(resId : Int, duration: Int = Toast.LENGTH_SHORT) {
    toast(resources.getString(resId))
}

fun Any.toast(context: Context, content: String, duration: Int = Toast.LENGTH_SHORT) {
    context.toast(content, duration)
}

fun Any.toast(context: Context, @StringRes id: Int, duration: Int=Toast.LENGTH_SHORT) {
    context.toast(id, duration)
}