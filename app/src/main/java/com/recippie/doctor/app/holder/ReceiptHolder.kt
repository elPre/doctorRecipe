package com.recippie.doctor.app.holder

import com.recippie.doctor.app.databinding.ReceiptItemBinding
import com.recippie.doctor.app.event.ReceiptActionEvent
import com.recippie.doctor.app.pojo.Receipt

class ReceiptHolder(
    private val binding: ReceiptItemBinding,
    val onAction: (ReceiptActionEvent) -> Unit,
    val getItem: (Int) -> Receipt
) : BaseBindingViewHolder<Receipt, ReceiptItemBinding>(binding) {

    init {
        with(binding) {
            etDescription.setOnClickListener {
                getItem(bindingAdapterPosition).run {
                    onAction(ReceiptActionEvent.HideButtons)
                }
            }
            etDuringTime.setOnClickListener {
                getItem(bindingAdapterPosition).run {
                    onAction(ReceiptActionEvent.HideButtons)
                }
            }
            etEachTime.setOnClickListener {
                getItem(bindingAdapterPosition).run {
                    onAction(ReceiptActionEvent.HideButtons)
                }
            }
        }
    }

    override fun bind(item: Receipt) = with(binding) {
        etDescription.setText(item.description)
        etDuringTime.setText(item.duringTime)
        etEachTime.setText(item.eachTime)
        tvReceiptNumber.text = (item.numReceipt ?: "").toString()
        tvNumMedicine.text = (item.numMedicine ?: "0").toString()
    }
}