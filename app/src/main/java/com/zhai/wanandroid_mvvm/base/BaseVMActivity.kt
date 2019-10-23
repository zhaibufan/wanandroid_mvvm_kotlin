package com.zhai.wanandroid_mvvm.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get

/**
 * @author zhaixiaofan
 * @date 2019-10-23 21:25
 */
abstract class BaseVMActivity<VM : BaseViewModel> : AppCompatActivity(), LifecycleObserver {

    lateinit var mViewModel : VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVM()
        startObserve()
        setContentView(getLayoutResId())
        initView()
        initData()
    }

    private fun initVM() {
        providerVMClass<VM>()?.let {
            mViewModel = ViewModelProvider(this).get(it)
            mViewModel.let {
                lifecycle::addObserver
            }
        }
    }

    open fun <VM> providerVMClass() : Class<VM>? = null

    open fun startObserve() {
        mViewModel.mExceptions.observe(this, Observer { t -> onError(t) })
    }

    open fun onError(e: Throwable) {}

    abstract fun getLayoutResId(): Int
    abstract fun initView()
    abstract fun initData()

    override fun onDestroy() {
        lifecycle.removeObserver(mViewModel)
        super.onDestroy()
    }

}