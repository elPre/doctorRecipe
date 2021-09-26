package com.recippie.doctor.app.holder

import com.recippie.doctor.app.databinding.RecipeItemBinding
import com.recippie.doctor.app.event.RecipeActionEvent
import com.recippie.doctor.app.moduleitems.ModuleItemDataWrapper
import com.recippie.doctor.app.pojo.RecipeModuleItem

class RecipeHolder(
    val binding: RecipeItemBinding,
    val onAction: (RecipeActionEvent) -> Unit
) : RecipeBaseViewHolder<ModuleItemDataWrapper<RecipeModuleItem>, RecipeItemBinding>(binding) {
    override val ivLoading = null

    override fun bind(item: ModuleItemDataWrapper<RecipeModuleItem>) = with(binding) {
        with(item.data) {
            //tvDescription.setText()
        }
    }
}