package com.recippie.doctor.app.holder

import androidx.recyclerview.widget.LinearLayoutManager
import com.recippie.doctor.app.adapter.HistoryBaseAdapter
import com.recippie.doctor.app.databinding.HistoryInfoItemBinding
import com.recippie.doctor.app.event.CurrentHistoryActionEvent
import com.recippie.doctor.app.moduleitems.ModuleItemDataWrapper
import com.recippie.doctor.app.pojo.HistoryInfo

class HistoryParentViewHolder(
    val binding: HistoryInfoItemBinding,
    onAction: (CurrentHistoryActionEvent) -> Unit,
) : HistoryBaseViewHolder<ModuleItemDataWrapper<HistoryInfo>, HistoryInfoItemBinding>(binding) {

    override val ivLoading = null

    private val adapter = HistoryBaseAdapter(onAction)

    init {
        with(binding.rvHistoryInfoItem) {
            layoutManager = LinearLayoutManager(context)
            adapter = this@HistoryParentViewHolder.adapter
        }
    }

    override fun bind(item: ModuleItemDataWrapper<HistoryInfo>) {
        with(item) {
            adapter.moduleData = data
        }
    }
}