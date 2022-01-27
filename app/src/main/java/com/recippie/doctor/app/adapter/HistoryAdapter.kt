package com.recippie.doctor.app.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.recippie.doctor.app.databinding.BannerHolderHistoryReceiptBinding
import com.recippie.doctor.app.databinding.HeaderInfoReceiptItemBinding
import com.recippie.doctor.app.databinding.HistoryInfoItemBinding
import com.recippie.doctor.app.event.CurrentHistoryActionEvent
import com.recippie.doctor.app.holder.HistoryBannerViewHolder
import com.recippie.doctor.app.holder.HistoryBaseViewHolder
import com.recippie.doctor.app.holder.HistoryEmptyViewHolder
import com.recippie.doctor.app.holder.HistoryParentViewHolder
import com.recippie.doctor.app.moduleitems.ModuleItemDataWrapper
import com.recippie.doctor.app.pojo.HistoryModuleItem
import com.recippie.doctor.app.pojo.HistoryType
import com.recippie.doctor.app.util.inflater

typealias HistoryViewHolder = HistoryBaseViewHolder<ModuleItemDataWrapper<HistoryModuleItem>, ViewBinding>

class HistoryAdapter(
    private val onAction: (CurrentHistoryActionEvent) -> Unit
) : ListAdapter<ModuleItemDataWrapper<HistoryModuleItem>, HistoryViewHolder>(HistoryBaseItemDiff) {

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (HistoryType.values()[viewType]) {
            HistoryType.HEADER -> HistoryEmptyViewHolder(
                HeaderInfoReceiptItemBinding.inflate(
                    parent.inflater,
                    parent,
                    false
                ), onAction
            )
            HistoryType.HISTORY_INFO -> HistoryParentViewHolder(
                HistoryInfoItemBinding.inflate(
                    parent.inflater,
                    parent,
                    false
                ), onAction
            )
            HistoryType.BANNER -> HistoryBannerViewHolder(
                BannerHolderHistoryReceiptBinding.inflate(
                    parent.inflater,
                    parent,
                    false
                ), onAction
            )
        } as HistoryViewHolder

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) = holder.bind(getItem(position))

    override fun getItemViewType(position: Int) = getItem(position).data.itemType.ordinal
}

object HistoryBaseItemDiff :
    DiffUtil.ItemCallback<ModuleItemDataWrapper<HistoryModuleItem>>() {
    override fun areItemsTheSame(
        oldItem: ModuleItemDataWrapper<HistoryModuleItem>,
        newItem: ModuleItemDataWrapper<HistoryModuleItem>
    ) =
        oldItem.data.id == newItem.data.id

    override fun areContentsTheSame(
        oldItem: ModuleItemDataWrapper<HistoryModuleItem>,
        newItem: ModuleItemDataWrapper<HistoryModuleItem>
    ) =
        oldItem.data == newItem.data && oldItem.loadingState == newItem.loadingState
}