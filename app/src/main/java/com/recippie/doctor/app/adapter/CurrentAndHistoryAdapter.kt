package com.recippie.doctor.app.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.recippie.doctor.app.databinding.BannerHolderCurrentReceiptBinding
import com.recippie.doctor.app.databinding.CurrentHistoryInfoItemBinding
import com.recippie.doctor.app.databinding.HeaderInfoReceiptItemBinding
import com.recippie.doctor.app.event.CurrentHistoryActionEvent
import com.recippie.doctor.app.holder.CurrentAndHistoryBannerViewHolder
import com.recippie.doctor.app.holder.CurrentAndHistoryBaseViewHolder
import com.recippie.doctor.app.holder.CurrentAndHistoryEmptyViewHolder
import com.recippie.doctor.app.holder.CurrentAndHistoryParentViewHolder
import com.recippie.doctor.app.moduleitems.ModuleItemDataWrapper
import com.recippie.doctor.app.pojo.CurrentModuleItem
import com.recippie.doctor.app.pojo.CurrentType
import com.recippie.doctor.app.util.inflater

typealias CurrentHistoryBaseViewHolder = CurrentAndHistoryBaseViewHolder<ModuleItemDataWrapper<CurrentModuleItem>, ViewBinding>

class CurrentAndHistoryAdapter(
    private val onAction: (CurrentHistoryActionEvent) -> Unit
) : ListAdapter<ModuleItemDataWrapper<CurrentModuleItem>, CurrentHistoryBaseViewHolder>(
    CurrentAndHistoryBaseItemDiff
) {

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (CurrentType.values()[viewType]) {
            CurrentType.EMPTY -> CurrentAndHistoryEmptyViewHolder(
                HeaderInfoReceiptItemBinding.inflate(
                    parent.inflater,
                    parent,
                    false
                ), onAction
            )
            CurrentType.INFO -> CurrentAndHistoryParentViewHolder(
                CurrentHistoryInfoItemBinding.inflate(
                    parent.inflater,
                    parent,
                    false
                ), onAction
            )
            CurrentType.BANNER -> CurrentAndHistoryBannerViewHolder(
                BannerHolderCurrentReceiptBinding.inflate(
                    parent.inflater,
                    parent,
                    false
                ), onAction
            )
        } as CurrentHistoryBaseViewHolder

    override fun onBindViewHolder(holder: CurrentHistoryBaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int) = getItem(position).data.itemType.ordinal

}

object CurrentAndHistoryBaseItemDiff :
    DiffUtil.ItemCallback<ModuleItemDataWrapper<CurrentModuleItem>>() {
    override fun areItemsTheSame(
        oldItem: ModuleItemDataWrapper<CurrentModuleItem>,
        newItem: ModuleItemDataWrapper<CurrentModuleItem>
    ) =
        oldItem.data.id == newItem.data.id

    override fun areContentsTheSame(
        oldItem: ModuleItemDataWrapper<CurrentModuleItem>,
        newItem: ModuleItemDataWrapper<CurrentModuleItem>
    ) =
        oldItem.data == newItem.data && oldItem.loadingState == newItem.loadingState
}