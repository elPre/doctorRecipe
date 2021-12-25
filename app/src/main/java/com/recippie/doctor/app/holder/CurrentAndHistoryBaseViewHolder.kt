package com.recippie.doctor.app.holder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.recippie.doctor.app.moduleitems.IValidateLoadingState
import com.recippie.doctor.app.moduleitems.ModuleItemDataWrapper
import com.recippie.doctor.app.pojo.CurrentHistoryModuleItem

abstract class CurrentAndHistoryBaseViewHolder<T : ModuleItemDataWrapper<CurrentHistoryModuleItem>, VB : ViewBinding>
    (binding: VB,
     private val getModuleItem: ((Int) -> CurrentHistoryModuleItem?)? = null)
    : RecyclerView.ViewHolder(binding.root), IValidateLoadingState {

    val moduleItem: CurrentHistoryModuleItem?
        get() = getModuleItem?.invoke(bindingAdapterPosition)

    abstract fun bind(item: T)
}