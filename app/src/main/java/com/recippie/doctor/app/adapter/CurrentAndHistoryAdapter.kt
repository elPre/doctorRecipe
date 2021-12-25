package com.recippie.doctor.app.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.recippie.doctor.app.databinding.CurrentHistoryInfoItemBinding
import com.recippie.doctor.app.databinding.HeaderInfoReceiptItemBinding
import com.recippie.doctor.app.databinding.MedicineIntakeScheduleItemBinding
import com.recippie.doctor.app.event.CurrentHistoryActionEvent
import com.recippie.doctor.app.holder.CurrentAndHistoryBaseViewHolder
import com.recippie.doctor.app.holder.CurrentAndHistoryEmptyViewHolder
import com.recippie.doctor.app.holder.CurrentAndHistoryInfoViewHolder
import com.recippie.doctor.app.holder.CurrentAndHistoryParentViewHolder
import com.recippie.doctor.app.moduleitems.ModuleItemDataWrapper
import com.recippie.doctor.app.pojo.CurrentHistoryModuleItem
import com.recippie.doctor.app.pojo.CurrentHistoryType
import com.recippie.doctor.app.util.inflater

typealias CurrentHistoryBaseViewHolder = CurrentAndHistoryBaseViewHolder<ModuleItemDataWrapper<CurrentHistoryModuleItem>, ViewBinding>

class CurrentAndHistoryAdapter(
    private val onAction: (CurrentHistoryActionEvent) -> Unit
) : ListAdapter<ModuleItemDataWrapper<CurrentHistoryModuleItem>, CurrentHistoryBaseViewHolder>(
    CurrentAndHistoryBaseItemDiff
) {

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (CurrentHistoryType.values()[viewType]) {
            CurrentHistoryType.EMPTY -> CurrentAndHistoryEmptyViewHolder(
                HeaderInfoReceiptItemBinding.inflate(
                    parent.inflater,
                    parent,
                    false
                ), onAction
            )
            CurrentHistoryType.INFO -> CurrentAndHistoryParentViewHolder(
                CurrentHistoryInfoItemBinding.inflate(
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
    DiffUtil.ItemCallback<ModuleItemDataWrapper<CurrentHistoryModuleItem>>() {
    override fun areItemsTheSame(
        oldItem: ModuleItemDataWrapper<CurrentHistoryModuleItem>,
        newItem: ModuleItemDataWrapper<CurrentHistoryModuleItem>
    ) =
        oldItem.data.id == newItem.data.id

    override fun areContentsTheSame(
        oldItem: ModuleItemDataWrapper<CurrentHistoryModuleItem>,
        newItem: ModuleItemDataWrapper<CurrentHistoryModuleItem>
    ) =
        oldItem.data == newItem.data && oldItem.loadingState == newItem.loadingState
}