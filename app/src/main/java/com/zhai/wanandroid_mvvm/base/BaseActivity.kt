package com.zhai.wanandroid_mvvm.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * @author zhaixiaofan
 * @date 2019-10-23 21:13
 */
abstract class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        initView()
        initData()
    }

    abstract fun initView()

    abstract fun initData()

    abstract fun getLayoutResId(): Int

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}