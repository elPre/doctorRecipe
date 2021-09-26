package com.recippie.doctor.app.holder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.recippie.doctor.app.moduleitems.IValidateLoadingState
import com.recippie.doctor.app.moduleitems.ModuleItemDataWrapper
import com.recippie.doctor.app.pojo.RecipeModuleItem

abstract class RecipeBaseViewHolder<T : ModuleItemDataWrapper<RecipeModuleItem>, VB : ViewBinding>(
    binding: VB,
    private val getModuleItem: ((Int) -> RecipeModuleItem?)? = null,
) : RecyclerView.ViewHolder(binding.root), IValidateLoadingState {

    val moduleItem: RecipeModuleItem?
        get() = getModuleItem?.invoke(bindingAdapterPosition)

    abstract fun bind(item: T)
}