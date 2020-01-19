package com.zhai.wanandroid_mvvm.ui.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhai.wanandroid_mvvm.R
import com.beyondsoft.mvvm.base.BaseVMActivity
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

    private val onQueryTextListener = object : androidx.appcompat.widget.SearchView.OnQueryTextListener {

        override fun onQueryTextChange(newText: String?) = false

        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.run {
                key = query
                currentPage = 0
                startSearch(currentPage, key)
            }
            return true
        }
    }

    override fun initView() {
        initTagLayout()
        initRecyclerView()
        searchRefreshLayout.setOnRefreshListener {
            currentPage = 0
            startSearch(currentPage, key)
        }
        search_view.run {
            //设置搜索框直接展开显示。左侧有放大镜(在搜索框中) 右侧有叉叉 可以关闭搜索框
            isIconified = false
            //设置搜索框直接展开显示。左侧有无放大镜(在搜索框中) 右侧无叉叉 有输入内容后有叉叉 不能关闭搜索框
            onActionViewExpanded()
            setOnQueryTextListener(onQueryTextListener)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }
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
        search_view.clearFocus()
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

    override fun onBackPressed() {
        if (sv_tag.visibility == View.GONE) {
            sv_tag.visibility = View.VISIBLE
            searchRecycleView.visibility = View.GONE
            mSearchAdapter.setNewData(null)
        } else {
            finish()
        }
    }
}