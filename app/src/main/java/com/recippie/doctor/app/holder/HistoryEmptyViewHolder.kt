package com.recippie.doctor.app.holder

import com.recippie.doctor.app.databinding.HeaderInfoReceiptItemBinding
import com.recippie.doctor.app.event.CurrentHistoryActionEvent
import com.recippie.doctor.app.moduleitems.ModuleItemDataWrapper
import com.recippie.doctor.app.pojo.HeaderData

class HistoryEmptyViewHolder(
    val binding: HeaderInfoReceiptItemBinding,
    val onAction: (CurrentHistoryActionEvent) -> Unit,
) : HistoryBaseViewHolder<ModuleItemDataWrapper<HeaderData>, HeaderInfoReceiptItemBinding>(binding) {

    override val ivLoading = null

    override fun bind(item: ModuleItemDataWrapper<HeaderData>) {  }
}