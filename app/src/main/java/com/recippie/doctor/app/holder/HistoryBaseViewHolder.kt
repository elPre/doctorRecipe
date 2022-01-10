package com.recippie.doctor.app.holder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.recippie.doctor.app.moduleitems.IValidateLoadingState
import com.recippie.doctor.app.moduleitems.ModuleItemDataWrapper
import com.recippie.doctor.app.pojo.HistoryModuleItem

abstract class HistoryBaseViewHolder<T : ModuleItemDataWrapper<HistoryModuleItem>, VB : ViewBinding>
    (binding: VB,
     private val getModuleItem: ((Int) -> HistoryModuleItem?)? = null)
    : RecyclerView.ViewHolder(binding.root), IValidateLoadingState {

    val moduleItem: HistoryModuleItem?
        get() = getModuleItem?.invoke(bindingAdapterPosition)

    abstract fun bind(item: T)
}