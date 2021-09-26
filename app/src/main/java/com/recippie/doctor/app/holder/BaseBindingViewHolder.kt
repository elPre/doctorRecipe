package com.beachbody.bod.util.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseBindingViewHolder<T, VB : ViewBinding>(private val viewBinding: VB) : RecyclerView.ViewHolder(viewBinding.root) {
    abstract fun bind(item: T)
}