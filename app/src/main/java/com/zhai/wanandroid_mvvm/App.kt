package com.zhai.wanandroid_mvvm

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates
import com.tencent.smtt.sdk.QbSdk
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.tencent.smtt.sdk.WebView
import com.zhai.wanandroid_mvvm.ui.widget.X5WebView


class App : Application() {

    companion object {
        var CONTEXT: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext

        val cb = object : QbSdk.PreInitCallback {
            override fun onViewInitFinished(arg0: Boolean) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            }

            override fun onCoreInitFinished() {

            }
        }
        //x5内核初始化接口
        QbSdk.initX5Environment(applicationContext, cb)
    }
}