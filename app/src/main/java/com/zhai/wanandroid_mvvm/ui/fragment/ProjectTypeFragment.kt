package com.zhai.wanandroid_mvvm.ui.fragment

import android.os.Bundle
import com.zhai.wanandroid_mvvm.R
import com.zhai.wanandroid_mvvm.base.BaseVMFragment
import com.zhai.wanandroid_mvvm.vm.HomeViewModel

class ProjectTypeFragment : BaseVMFragment<HomeViewModel>() {

    private val cid by lazy { arguments?.getInt(CID) }
    private val isLasted by lazy { arguments?.getBoolean(LASTED) }
    override fun providerVMClass(): Class<HomeViewModel>? = HomeViewModel::class.java

    override fun getLayoutResId(): Int = R.layout.fragment_project_type

    override fun initView() {
    }

    override fun initData() {
    }

    companion object {
        private const val CID = "projectCid"
        private const val LASTED = "lasted"
        fun newInstance(cid : Int, isLasted: Boolean) : ProjectTypeFragment{
            val mFragment = ProjectTypeFragment()
            val bundle = Bundle()
            bundle.putInt(CID, cid)
            bundle.putBoolean(LASTED, isLasted)
            mFragment.arguments = bundle
            return mFragment
        }
    }
}