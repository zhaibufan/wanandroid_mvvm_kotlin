package com.zhai.wanandroid_mvvm.ui.fragment

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhai.wanandroid_mvvm.R
import com.zhai.wanandroid_mvvm.base.BaseVMFragment
import com.zhai.wanandroid_mvvm.model.bean.ArticleList
import com.zhai.wanandroid_mvvm.ui.adapter.HomeArticleAdapter
import com.zhai.wanandroid_mvvm.vm.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*



class HomeFragment : BaseVMFragment<HomeViewModel>() {

    val TAG = "HomeFragment"
    private var pagerIndex = 0
    private val mLayoutManager by lazy { LinearLayoutManager(context) }
    private val mArticleAdapter by lazy { HomeArticleAdapter() }
    override fun providerVMClass(): Class<HomeViewModel>? = HomeViewModel::class.java
    override fun getLayoutResId(): Int = R.layout.fragment_home

    override fun initView() {
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

    private fun initAdapter() {
        mArticleAdapter.run {
            setOnItemClickListener { adapter, view, position -> Toast.makeText(context, position, Toast.LENGTH_LONG).show() }
            setOnLoadMoreListener({loadMore()}, mRecyclerView)
        }
    }

    private fun loadMore() {
        mArticleAdapter.isLoadMoreEnable
        pagerIndex ++
        mViewModel.getHomeArticles(pagerIndex)
    }

    private fun refresh() {
        mViewModel.getHomeArticles(0)
    }

    override fun initData() {
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            articleData.observe(this@HomeFragment, Observer { articleList -> updateArticleData(articleList) })
        }
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
}