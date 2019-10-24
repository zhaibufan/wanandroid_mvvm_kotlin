package com.zhai.wanandroid_mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get

/**
 * @author zhaixiaofan
 * @date 2019-10-23 21:44
 */
abstract class BaseVMFragment<VM : BaseViewModel> : Fragment(), LifecycleObserver {

    lateinit var mViewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initVM()
        initView()
        initData()
        startObserve()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initVM() {
        providerVMClass()?.let {
            mViewModel = ViewModelProvider(this).get(it)
            mViewModel.let {
                lifecycle::addObserver
            }
        }
    }

    open fun providerVMClass() : Class<VM>? = null

    abstract fun getLayoutResId(): Int

    abstract fun initView()

    abstract fun initData()

    open fun startObserve() {
        mViewModel.mExceptions.observe(this, Observer { it?.let { onError(it) } })
    }

    open fun onError(e: Throwable) {}

    override fun onDestroy() {
        lifecycle.removeObserver(mViewModel)
        super.onDestroy()
    }
}