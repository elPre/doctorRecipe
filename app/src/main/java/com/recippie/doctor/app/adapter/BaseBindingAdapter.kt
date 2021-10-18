package com.recippie.doctor.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.recippie.doctor.app.holder.BaseBindingViewHolder
import com.recippie.doctor.app.util.inflater


abstract class BaseBindingAdapter<T, VB : ViewBinding>(private var items: MutableList<T> = mutableListOf()) : RecyclerView.Adapter<BaseBindingViewHolder<T, VB>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = getViewHolder(parent.inflater, parent, viewType)

    override fun onBindViewHolder(holder: BaseBindingViewHolder<T, VB>, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    fun setData(items: MutableList<T>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun updateData(position: Int, item: T) {
        this.items[position] = item
        notifyItemChanged(position)
    }

    fun addData(item: T) {
        this.items.add(0, item)
        notifyItemInserted(0)
    }

    fun removeData(position: Int) {
        this.items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getItem(position: Int) = items[position]

    abstract fun getViewHolder(inflater: LayoutInflater, container: ViewGroup?, viewType: Int): BaseBindingViewHolder<T, VB>
}