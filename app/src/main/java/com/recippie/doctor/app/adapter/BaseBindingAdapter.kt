package com.recippie.doctor.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.recippie.doctor.app.holder.BaseBindingViewHolder
import com.recippie.doctor.app.util.inflater


abstract class BaseBindingAdapter<T, VB : ViewBinding>(private var items: List<T> = listOf()) : RecyclerView.Adapter<BaseBindingViewHolder<T, VB>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = getViewHolder(parent.inflater, parent, viewType)

    override fun onBindViewHolder(holder: BaseBindingViewHolder<T, VB>, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    fun setData(items: List<T>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun updateData(position: Int, item: T) {
        this.items.toMutableList()[position] = item
        notifyItemChanged(position)
    }

    fun getItem(position: Int) = items[position]

    abstract fun getViewHolder(inflater: LayoutInflater, container: ViewGroup?, viewType: Int): BaseBindingViewHolder<T, VB>
}