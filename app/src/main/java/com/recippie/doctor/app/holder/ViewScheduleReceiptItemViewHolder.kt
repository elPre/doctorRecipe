package com.recippie.doctor.app.holder

import com.recippie.doctor.app.databinding.MedicineIntakeScheduleItemBinding
import com.recippie.doctor.app.event.ReceiptActionEvent
import com.recippie.doctor.app.pojo.ViewScheduleReceipt

class ViewScheduleReceiptItemViewHolder(
    private val viewBinding: MedicineIntakeScheduleItemBinding,
    private val onAction: (ReceiptActionEvent) -> Unit,
    private val getItem: (Int) -> ViewScheduleReceipt?
): BaseBindingViewHolder<ViewScheduleReceipt, MedicineIntakeScheduleItemBinding>(viewBinding) {

    override fun bind(item: ViewScheduleReceipt) = with(viewBinding) {
        tvMedicineName.text = item.medicineName
        tvDate.text = item.date
        tvTime.text = item.time
    }
}