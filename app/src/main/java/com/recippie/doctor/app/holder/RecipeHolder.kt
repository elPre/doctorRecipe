package com.recippie.doctor.app.holder

import com.recippie.doctor.app.databinding.RecipeItemBinding
import com.recippie.doctor.app.event.RecipeActionEvent
import com.recippie.doctor.app.pojo.Recipe

class RecipeHolder(
    private val binding: RecipeItemBinding,
    val onAction: (RecipeActionEvent) -> Unit,
) : BaseBindingViewHolder<Recipe, RecipeItemBinding>(binding) {

    override fun bind(item: Recipe) = with(binding) {
        tvDescription.setText(item.description)
        tvDuringTime.setText(item.duringTime)
        tvEachTime.setText(item.eachTime)
    }
}