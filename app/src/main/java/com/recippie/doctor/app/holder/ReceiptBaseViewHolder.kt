package com.recippie.doctor.app.holder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.recippie.doctor.app.moduleitems.IValidateLoadingState
import com.recippie.doctor.app.moduleitems.ModuleItemDataWrapper
import com.recippie.doctor.app.pojo.ReceiptModuleItem

abstract class ReceiptBaseViewHolder<T : ModuleItemDataWrapper<ReceiptModuleItem>, VB : ViewBinding>(
    binding: VB,
    private val getModuleItem: ((Int) -> ReceiptModuleItem?)? = null,
) : RecyclerView.ViewHolder(binding.root), IValidateLoadingState {

    val moduleItem: ReceiptModuleItem?
        get() = getModuleItem?.invoke(bindingAdapterPosition)

    abstract fun bind(item: T)
}