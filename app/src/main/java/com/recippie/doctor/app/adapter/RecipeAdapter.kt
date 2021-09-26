package com.recippie.doctor.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.recippie.doctor.app.databinding.RecipeItemBinding
import com.recippie.doctor.app.event.RecipeActionEvent
import com.recippie.doctor.app.holder.BaseBindingViewHolder
import com.recippie.doctor.app.holder.RecipeHolder
import com.recippie.doctor.app.pojo.Recipe
import com.recippie.doctor.app.util.inflater

class RecipeAdapter(
    private val onAction: (RecipeActionEvent) -> Unit,
) : BaseBindingAdapter<Recipe, RecipeItemBinding>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder {
        return RecipeHolder(
            RecipeItemBinding.inflate(parent.inflater, parent, false), onAction
        )
    }

    override fun getViewHolder(inflater: LayoutInflater, container: ViewGroup?, viewType: Int):
            BaseBindingViewHolder<Recipe, RecipeItemBinding> {
        val binding = RecipeItemBinding.inflate(inflater, container, false)
        return RecipeHolder(binding, onAction)
    }
}