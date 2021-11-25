package com.recippie.doctor.app.holder

import com.recippie.doctor.app.databinding.ReceiptItemBinding
import com.recippie.doctor.app.event.ReceiptActionEvent
import com.recippie.doctor.app.pojo.Receipt

class ReceiptHolder(
    private val binding: ReceiptItemBinding,
    val onAction: (ReceiptActionEvent) -> Unit,
) : BaseBindingViewHolder<Receipt, ReceiptItemBinding>(binding) {

    override fun bind(item: Receipt) = with(binding) {
        etDescription.setText(item.description)
        etDuringTime.setText(item.duringTime)
        etEachTime.setText(item.eachTime)
        tvReceiptNumber.text = (item.numReceipt ?: "").toString()
    }
}