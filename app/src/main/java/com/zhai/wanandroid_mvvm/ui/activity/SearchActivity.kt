package com.zhai.wanandroid_mvvm.ui.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhai.wanandroid_mvvm.R
import com.zhai.wanandroid_mvvm.base.BaseVMActivity
import com.zhai.wanandroid_mvvm.model.bean.ArticleList
import com.zhai.wanandroid_mvvm.model.bean.Hot
import com.zhai.wanandroid_mvvm.ui.adapter.HomeArticleAdapter
import com.zhai.wanandroid_mvvm.vm.SearchViewModel
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseVMActivity<SearchViewModel>() {

    private val hotList = mutableListOf<Hot>()
    private val webSiteList = mutableListOf<Hot>()
    private val searchList = mutableListOf<ArticleList>()
    private val mSearchAdapter by lazy { HomeArticleAdapter() }
    private val mLayoutManager by lazy { LinearLayoutManager(this) }
    private var key = ""
    private var currentPage = 0

    override fun providerVMClass(): Class<SearchViewModel>? = SearchViewModel::class.java
    override fun getLayoutResId(): Int = R.layout.activity_search

    override fun initView() {
        initTagLayout()
        initRecyclerView()
        searchRefreshLayout.setOnRefreshListener {
            currentPage = 0
            startSearch(currentPage, key)
        }
    }

    override fun initData() {
        mViewModel.getHotSearch()
        mViewModel.getWebSites()
    }

    private fun initTagLayout() {
        hotTagLayout.run {
            adapter = object : TagAdapter<Hot>(hotList) {
                override fun getView(parent: FlowLayout, position: Int, t: Hot): View {
                    val tv = LayoutInflater.from(parent.context).inflate(R.layout.adapter_tag, parent, false) as TextView
                    tv.text = t.name
                    return tv
                }
            }
            setOnTagClickListener { _, position, _ ->
                key = hotList[position].name
                currentPage = 0
                startSearch(currentPage, key)
                true
            }
        }

        webTagLayout.run {
            adapter = object : TagAdapter<Hot>(webSiteList) {
                override fun getView(parent: FlowLayout, position: Int, t: Hot): View {
                    val tv = LayoutInflater.from(parent.context).inflate(R.layout.adapter_tag, parent, false) as TextView
                    tv.text = t.name
                    return tv
                }
            }
        }
    }

    private fun initRecyclerView() {
        searchRecycleView.run {
            layoutManager = mLayoutManager
            adapter = mSearchAdapter
        }
        mSearchAdapter.run {
            setOnLoadMoreListener({ startSearch(currentPage, key)}, searchRecycleView)
        }
        val emptyView = layoutInflater.inflate(R.layout.empty_view, searchRecycleView.parent as ViewGroup, false)
        val emptyTv = emptyView.findViewById<TextView>(R.id.emptyTv)
        emptyTv.text = getString(R.string.try_another_key)
        mSearchAdapter.emptyView = emptyView
    }

    /**
     * 开始搜索
     */
    private fun startSearch(page : Int, key : String) {
        if (page == 0) {
            searchList.clear()
            searchRefreshLayout.isRefreshing = true
        }
        mViewModel.searchHot(page, key)
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            mHotSearch.observe(this@SearchActivity, Observer {
                it.run {
                    hotList.clear()
                    hotList.addAll(it)
                    hotTagLayout.adapter.notifyDataChanged()
                }
            })
            mWebSiteHot.observe(this@SearchActivity, Observer {
                it.run {
                    webSiteList.clear()
                    webSiteList.addAll(it)
                    webTagLayout.adapter.notifyDataChanged()
                }
            })
            mArticleList.observe(this@SearchActivity, Observer {
                sv_tag.visibility = View.GONE
                searchRecycleView.visibility = View.VISIBLE
                updateSearchResult(it)
            })
        }
    }

    private fun updateSearchResult(articleList: ArticleList) {
        mSearchAdapter.run {
            if (articleList.datas.isEmpty()) {
                searchRefreshLayout.isRefreshing = false
                if (currentPage == 0) {
                    data.clear()
                    notifyItemRangeRemoved(0, data.size)
                }
                loadMoreEnd()
                return
            }
            if (articleList.offset >= articleList.total) {
                loadMoreEnd()
                return
            }
            if (searchRefreshLayout.isRefreshing) {
                replaceData(articleList.datas)
            } else {
                addData(articleList.datas)
            }
            setEnableLoadMore(true)
            loadMoreComplete()

            searchRefreshLayout.isRefreshing = false
            currentPage++
        }
    }
}