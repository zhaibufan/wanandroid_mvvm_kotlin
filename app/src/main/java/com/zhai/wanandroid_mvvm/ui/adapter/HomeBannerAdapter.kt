package com.zhai.wanandroid_mvvm.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zhai.wanandroid_mvvm.R
import com.zhai.wanandroid_mvvm.model.bean.Banner
import kotlinx.android.synthetic.main.item_banner.view.*

class HomeBannerAdapter(private val context : Context) : RecyclerView.Adapter<MyViewHolder>() {

    private var mData = listOf<Banner>()

    fun setData(data : List<Banner>) {
        mData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(LayoutInflater.from(context).inflate(
        R.layout.item_banner, parent, false))

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).run {
            load(mData[position].imagePath)
        }.into(holder.ivImage)
    }

}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val ivImage : ImageView = itemView.iv_item
}