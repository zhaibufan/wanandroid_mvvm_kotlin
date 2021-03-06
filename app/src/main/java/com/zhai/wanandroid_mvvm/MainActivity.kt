package com.zhai.wanandroid_mvvm

import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.material.navigation.NavigationView
import com.beyondsoft.mvvm.base.BaseActivity
import com.zhai.wanandroid_mvvm.ui.activity.LoginActivity
import com.zhai.wanandroid_mvvm.ui.activity.SearchActivity
import com.zhai.wanandroid_mvvm.ui.fragment.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener{

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_login -> { toActivity<LoginActivity>()}
            else -> {}
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private val tabList = arrayOf("首页", "广场","最新项目", "体系", "导航")
    private val fragmentList = arrayListOf<Fragment>()
    private val homeFragment by lazy { HomeFragment() }
    private val squareFragment by lazy { SquareFragment() }
    private val projectTypeFragment by lazy { ProjectTypeFragment.newInstance(0, true) }
    private val systemFragment by lazy { SystemFragment() }
    private val navigationFragment by lazy { NavigationFragment() }
    private val fragmentManager by lazy { this.supportFragmentManager }

    private val mViewPagerAdapter = object : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment = fragmentList[position]

        override fun getCount(): Int = fragmentList.size

        override fun getPageTitle(position: Int): CharSequence? {
            return tabList[position]
        }
    }

    override fun getLayoutResId(): Int = R.layout.activity_main

    init {
        fragmentList.add(homeFragment)
        fragmentList.add(squareFragment)
        fragmentList.add(projectTypeFragment)
        fragmentList.add(systemFragment)
        fragmentList.add(navigationFragment)
    }

    override fun initView() {
        navigationView.setNavigationItemSelectedListener(this)
        initViewPager()
        mainToolBar.setNavigationOnClickListener { drawer.openDrawer(GravityCompat.START) }
        mainSearch.setOnClickListener { toActivity<SearchActivity>() }
    }

    override fun initData() {
    }

    private fun initViewPager() {
        viewPager.run {
            offscreenPageLimit = fragmentList.size
            adapter = mViewPagerAdapter
        }
        tabLayout.setupWithViewPager(viewPager)
    }
}
