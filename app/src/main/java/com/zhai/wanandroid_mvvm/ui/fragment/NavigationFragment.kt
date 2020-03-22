package com.zhai.wanandroid_mvvm.ui.fragment

import com.zhai.wanandroid_mvvm.R
import com.beyondsoft.mvvm.base.BaseVMFragment
import com.zhai.wanandroid_mvvm.vm.HomeViewModel
import com.zhai.wanandroid_mvvm.vm.NavigationViewModel

class NavigationFragment : BaseVMFragment<NavigationViewModel>() {

    override fun providerVMClass(): Class<NavigationViewModel>? = NavigationViewModel::class.java

    override fun getLayoutResId(): Int = R.layout.fragment_navigation

    override fun initView() {
    }

    override fun initData() {
    }

}