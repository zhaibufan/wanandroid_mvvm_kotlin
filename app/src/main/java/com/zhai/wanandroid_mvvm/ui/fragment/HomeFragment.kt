package com.zhai.wanandroid_mvvm.ui.fragment

import android.util.Log
import androidx.lifecycle.Observer
import com.zhai.wanandroid_mvvm.R
import com.zhai.wanandroid_mvvm.base.BaseVMFragment
import com.zhai.wanandroid_mvvm.vm.HomeViewModel

class HomeFragment : BaseVMFragment<HomeViewModel>() {

    val TAG = "HomeFragment"
    override fun providerVMClass(): Class<HomeViewModel>? = HomeViewModel::class.java
    override fun getLayoutResId(): Int = R.layout.fragment_home

    override fun initView() {
    }

    override fun initData() {
        mViewModel.getHomeArticles(0)
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            articleData.observe(this@HomeFragment, Observer { articleList -> Log.d(TAG, articleList.toString()) })
        }
    }
}