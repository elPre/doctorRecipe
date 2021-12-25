package com.recippie.doctor.app.holder

import androidx.recyclerview.widget.LinearLayoutManager
import com.recippie.doctor.app.adapter.CurrentHistoryBaseAdapter
import com.recippie.doctor.app.databinding.CurrentHistoryInfoItemBinding
import com.recippie.doctor.app.event.CurrentHistoryActionEvent
import com.recippie.doctor.app.moduleitems.ModuleItemDataWrapper
import com.recippie.doctor.app.pojo.ReceiptInfo
import com.recippie.doctor.app.pojo.ViewScheduleProgram

class CurrentAndHistoryParentViewHolder(
    val binding: CurrentHistoryInfoItemBinding,
    onAction: (CurrentHistoryActionEvent) -> Unit,
) : CurrentAndHistoryBaseViewHolder<ModuleItemDataWrapper<ReceiptInfo>, CurrentHistoryInfoItemBinding>(binding) {

    override val ivLoading = null

    private val adapter = CurrentHistoryBaseAdapter(onAction)

    init {
        with(binding.rvCurrentHistoryInfoItem) {
            layoutManager = LinearLayoutManager(context)
            adapter = this@CurrentAndHistoryParentViewHolder.adapter
        }
    }

    override fun bind(item: ModuleItemDataWrapper<ReceiptInfo>) {
        with(item) {
            adapter.moduleData = data
        }
    }
}