package com.zhai.wanandroid_mvvm.ui.activity

import com.zhai.wanandroid_mvvm.R
import com.zhai.wanandroid_mvvm.base.BaseVMActivity
import com.zhai.wanandroid_mvvm.vm.LoginViewModel

class LoginActivity : BaseVMActivity<LoginViewModel>() {

    override fun providerVMClass(): Class<LoginViewModel>? = LoginViewModel::class.java

    override fun getLayoutResId(): Int = R.layout.activity_login

    override fun initView() {
    }

    override fun initData() {
    }
}