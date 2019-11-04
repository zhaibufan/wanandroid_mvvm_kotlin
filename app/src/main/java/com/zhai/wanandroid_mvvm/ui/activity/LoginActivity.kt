package com.zhai.wanandroid_mvvm.ui.activity

import android.app.ProgressDialog
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.Observer
import com.zhai.wanandroid_mvvm.R
import com.zhai.wanandroid_mvvm.base.BaseVMActivity
import com.zhai.wanandroid_mvvm.utils.toast
import com.zhai.wanandroid_mvvm.vm.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseVMActivity<LoginViewModel>() {

    override fun providerVMClass(): Class<LoginViewModel>? = LoginViewModel::class.java

    override fun getLayoutResId(): Int = R.layout.activity_login

    override fun initData() {
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (userNameEt.text.isNullOrBlank() || passwordEt.text.isNullOrBlank()) {
                login.isEnabled = false
                register.isEnabled = false
            } else {
                login.isEnabled = true
                register.isEnabled = true
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

    override fun initView() {
        mToolbar.run {
            title = "登录注册"
            setNavigationIcon(R.drawable.arrow_back)
            setNavigationOnClickListener { onBackPressed() }
        }
        login.setOnClickListener { toLogin() }
        register.setOnClickListener { toRegister() }

        userNameEt.addTextChangedListener(textWatcher)
        passwordEt.addTextChangedListener(textWatcher)
    }

    private fun toRegister() {
        val userName = userNameEt.run { text }.run { toString() }.run { trim() }
        val pwd = passwordEt.text.toString().trim()
        mViewModel.register(userName, pwd)
    }

    private fun toLogin() {
        val userName = userNameEt.run { text }.run { toString() }.run { trim() }
        val pwd = passwordEt.text.toString().trim()
        mViewModel.login(userName, pwd)
    }

    override fun startObserve() {
        mViewModel.apply {
            uiState.observe(this@LoginActivity, Observer {
                if (it.showDialog) {
                    showDialog()
                } else {
                    cancelDialog()
                }
                it.apply {
                    if (isLogin) {
                        successData?.let {
                            toast("登录成功")
                            finish()
                        }
                    } else {
                        successData?.let {
                            toast("注册成功")
                            finish()
                        }
                    }
                }
                it.errorMsg?.run {
                    toast(this)
                }
            })
        }
    }

    private var progressDialog : ProgressDialog? = null
    private fun showDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(this)
        }
        progressDialog?.show()
    }
    private fun cancelDialog() {
        progressDialog?.cancel()
    }
}