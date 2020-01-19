package com.zhai.wanandroid_mvvm.ui.activity

import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.zhai.wanandroid_mvvm.R.layout.activity_h5
import com.beyondsoft.mvvm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_h5.*

class H5Activity : BaseActivity() {

    private val chromeClient = object : WebChromeClient() {
        override fun onProgressChanged(p0: WebView?, p1: Int) {
            super.onProgressChanged(p0, p1)
        }
    }

    override fun getLayoutResId(): Int = activity_h5

    override fun initView() {
        webView.run {
            loadUrl(intent.run { getStringExtra("url") })
            webChromeClient = chromeClient
        }
        mToolbar.run {
            title = intent.run { getStringExtra("title") }
            setNavigationIcon(com.zhai.wanandroid_mvvm.R.drawable.arrow_back)
            setNavigationOnClickListener { onBackPressed() }
        }
    }

    override fun initData() {
    }

    override fun onPause() {
        super.onPause()
        webView.onPause()
        webView.settings.lightTouchEnabled = false
    }

    //销毁 放置内存泄漏
    public override fun onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            webView.clearHistory()
            webView.destroy()
        }
        super.onDestroy()
    }
}