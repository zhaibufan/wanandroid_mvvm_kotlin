package com.zhai.wanandroid_mvvm.ui.widget

import android.content.Context
import android.util.AttributeSet
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

class X5WebView : WebView {

    private val client = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(p0: WebView?, p1: String?): Boolean {
            p0?.loadUrl(p1)
            return true
        }
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attr: AttributeSet?) : super(context, attr) {
        this.webViewClient = client
        initWebView()
    }

    private fun initWebView() {
        val webSetting = this.settings
        webSetting.javaScriptEnabled = true
        webSetting.javaScriptCanOpenWindowsAutomatically = true
        webSetting.allowFileAccess = true
        webSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        webSetting.setSupportZoom(true)
        webSetting.builtInZoomControls = true
        webSetting.useWideViewPort = true
        webSetting.setSupportMultipleWindows(true)
        webSetting.loadWithOverviewMode = true
        webSetting.setAppCacheEnabled(true)
        // webSetting.setDatabaseEnabled(true);
        webSetting.domStorageEnabled = true
        webSetting.setGeolocationEnabled(true)
        webSetting.setAppCacheMaxSize(java.lang.Long.MAX_VALUE)
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.pluginState = WebSettings.PluginState.ON_DEMAND
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.cacheMode = WebSettings.LOAD_NO_CACHE

        // this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
        // settings 的设计
    }

    //	@Override
    //	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
    //		boolean ret = super.drawChild(canvas, child, drawingTime);
    //		canvas.save();
    //		Paint paint = new Paint();
    //		paint.setColor(0x7fff0000);
    //		paint.setTextSize(24.f);
    //		paint.setAntiAlias(true);
    //		if (getX5WebViewExtension() != null) {
    //			canvas.drawText(this.getContext().getPackageName() + "-pid:"
    //					+ android.os.Process.myPid(), 10, 50, paint);
    //			canvas.drawText(
    //					"X5  Core:" + QbSdk.getTbsVersion(this.getContext()), 10,
    //					100, paint);
    //		} else {
    //			canvas.drawText(this.getContext().getPackageName() + "-pid:"
    //					+ android.os.Process.myPid(), 10, 50, paint);
    //			canvas.drawText("Sys Core", 10, 100, paint);
    //		}
    //		canvas.drawText(Build.MANUFACTURER, 10, 150, paint);
    //		canvas.drawText(Build.MODEL, 10, 200, paint);
    //		canvas.restore();
    //		return ret;
    //	}
}