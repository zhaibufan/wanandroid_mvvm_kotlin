package com.zhai.wanandroid_mvvm.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhai.wanandroid_mvvm.R
import com.zhai.wanandroid_mvvm.base.BaseVMFragment
import com.zhai.wanandroid_mvvm.model.bean.ArticleList
import com.zhai.wanandroid_mvvm.model.bean.Banner
import com.zhai.wanandroid_mvvm.onNetError
import com.zhai.wanandroid_mvvm.toActivity
import com.zhai.wanandroid_mvvm.ui.activity.H5Activity
import com.zhai.wanandroid_mvvm.ui.adapter.HomeArticleAdapter
import com.zhai.wanandroid_mvvm.ui.adapter.HomeBannerAdapter
import com.zhai.wanandroid_mvvm.utils.toast
import com.zhai.wanandroid_mvvm.vm.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.home_header_view.view.*


class HomeFragment : BaseVMFragment<HomeViewModel>() {

    val TAG = "HomeFragment"
    private var pagerIndex = 0
    private val mLayoutManager by lazy { LinearLayoutManager(context) }
    private val mArticleAdapter by lazy { HomeArticleAdapter() }
    private val mHeaderView by lazy { View.inflate(context, R.layout.home_header_view, null) }
    private val mBannerAdapter by lazy { HomeBannerAdapter(context!!) }
    override fun providerVMClass(): Class<HomeViewModel>? = HomeViewModel::class.java
    override fun getLayoutResId(): Int = R.layout.fragment_home

    override fun initView() {
        initBanner()
        initAdapter()
        mRecyclerView.run {
            layoutManager = mLayoutManager
            adapter = mArticleAdapter
        }
        homeRefreshLayout.run {
            isRefreshing = true
            setOnRefreshListener { refresh() }
        }
        refresh()
    }

    private fun initBanner() {
        mHeaderView.viewPager.run {
            adapter = mBannerAdapter
        }
    }

    private fun initAdapter() {
        mArticleAdapter.run {
            setOnItemClickListener { _, _, position ->
                val value = mViewModel.articleData.value!!.datas[position]
                val bundle = Bundle()
                bundle.putString("url", value.link)
                bundle.putString("title", value.title)
                activity?.toActivity<H5Activity>(extra = bundle)
            }
            setOnLoadMoreListener({loadMore()}, mRecyclerView)
            addHeaderView(mHeaderView)
        }
    }

    /**
     * 加载更多
     */
    private fun loadMore() {
        mArticleAdapter.isLoadMoreEnable
        pagerIndex ++
        getData(pagerIndex)
    }

    /**
     * 刷新
     */
    private fun refresh() {
        getData(0)
    }

    /**
     * 获取首页列表数据
     */
    private fun getData(pagerIndex : Int) {
        mViewModel.getHomeArticles(pagerIndex) {
            context?.toast("出错了")
            homeRefreshLayout.isRefreshing = false
            mArticleAdapter.loadMoreComplete()
        }
    }

    override fun initData() {
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            articleData.observe(this@HomeFragment, Observer { articleList -> updateArticleData(articleList) })
            mBanner.observe(this@HomeFragment, Observer { banner -> updateBannerData(banner) })
        }
    }

    private fun updateBannerData(banner: List<Banner>) {
        mBannerAdapter.setData(banner)
    }

    private fun updateArticleData(articleList: ArticleList) {
        mArticleAdapter.run {
            if (homeRefreshLayout.isRefreshing) {
                replaceData(articleList.datas)
                homeRefreshLayout.isRefreshing = false
            } else {
                addData(articleList.datas)
                loadMoreComplete()
            }
        }
    }

    override fun onError(e: Throwable) {
        super.onError(e)
//        Log.d(TAG, "onError = " + e.message)
//        context?.toast("没有网络")
//        homeRefreshLayout.isRefreshing = false
//        mArticleAdapter.loadMoreComplete()

        activity?.onNetError(e){
            Log.d(TAG, "onError = " + e.message)
            homeRefreshLayout.isRefreshing = false
            mArticleAdapter.loadMoreComplete()
        }
    }
}