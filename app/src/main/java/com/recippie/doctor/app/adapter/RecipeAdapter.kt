package com.recippie.doctor.app.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.recippie.doctor.app.databinding.RecipeItemBinding
import com.recippie.doctor.app.event.RecipeActionEvent
import com.recippie.doctor.app.holder.RecipeBaseViewHolder
import com.recippie.doctor.app.holder.RecipeHolder
import com.recippie.doctor.app.moduleitems.ModuleItemDataWrapper
import com.recippie.doctor.app.pojo.RecipeItemType
import com.recippie.doctor.app.pojo.RecipeModuleItem
import com.recippie.doctor.app.util.inflater

typealias RecipeBindingBaseViewHolder = RecipeBaseViewHolder<ModuleItemDataWrapper<RecipeModuleItem>, ViewBinding>

class RecipeAdapter(
    private val onAction: (RecipeActionEvent) -> Unit,
) : ListAdapter<ModuleItemDataWrapper<RecipeModuleItem>, RecipeBindingBaseViewHolder>(RecipeBaseItemDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (RecipeItemType.values()[viewType]) {
            RecipeItemType.RECIPE -> RecipeHolder(
                RecipeItemBinding.inflate(
                    parent.inflater,
                    parent,
                    false), onAction
            )

        } as RecipeBindingBaseViewHolder


    override fun onBindViewHolder(holder: RecipeBindingBaseViewHolder, position: Int) = holder.bind(getItem(position))

    override fun getItemViewType(position: Int) = getItem(position).data.itemType.ordinal

    fun getModuleItem(position: Int) = currentList.getOrNull(position)?.data
}

object RecipeBaseItemDiff : DiffUtil.ItemCallback<ModuleItemDataWrapper<RecipeModuleItem>>() {
    override fun areItemsTheSame(oldItem: ModuleItemDataWrapper<RecipeModuleItem>, newItem: ModuleItemDataWrapper<RecipeModuleItem>) =
        oldItem.data.id == newItem.data.id

    override fun areContentsTheSame(oldItem: ModuleItemDataWrapper<RecipeModuleItem>, newItem: ModuleItemDataWrapper<RecipeModuleItem>) =
        oldItem.data == newItem.data && oldItem.loadingState == newItem.loadingState
}