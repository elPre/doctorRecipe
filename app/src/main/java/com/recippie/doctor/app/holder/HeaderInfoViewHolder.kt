package com.recippie.doctor.app.holder

import com.recippie.doctor.app.databinding.HeaderInfoReceiptItemBinding
import com.recippie.doctor.app.event.ReceiptActionEvent
import com.recippie.doctor.app.pojo.HeaderInfoList

class HeaderInfoViewHolder(
    private val viewBinding: HeaderInfoReceiptItemBinding,
    private val onAction: (ReceiptActionEvent) -> Unit,
): BaseProgramViewHolder<HeaderInfoList>(viewBinding.root) {
    override fun bind(item: HeaderInfoList) { }
}