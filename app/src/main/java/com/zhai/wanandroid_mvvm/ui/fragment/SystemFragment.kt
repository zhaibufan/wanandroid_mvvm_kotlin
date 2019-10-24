package com.zhai.wanandroid_mvvm.ui.fragment

import com.zhai.wanandroid_mvvm.R
import com.zhai.wanandroid_mvvm.base.BaseVMFragment
import com.zhai.wanandroid_mvvm.vm.HomeViewModel

class SystemFragment : BaseVMFragment<HomeViewModel>() {

    override fun providerVMClass(): Class<HomeViewModel>? = HomeViewModel::class.java

    override fun getLayoutResId(): Int = R.layout.fragment_home

    override fun initView() {
    }

    override fun initData() {
    }
}