package com.recippie.doctor.app.holder

import androidx.core.content.ContextCompat.getColor
import com.recippie.doctor.app.R
import com.recippie.doctor.app.databinding.MedicineIntakeScheduleItemBinding
import com.recippie.doctor.app.event.ReceiptActionEvent
import com.recippie.doctor.app.pojo.ViewScheduleReceipt

class ViewScheduleReceiptItemViewHolder(
    private val viewBinding: MedicineIntakeScheduleItemBinding,
    private val onAction: (ReceiptActionEvent) -> Unit,
    private val getItem: (Int) -> ViewScheduleReceipt?
): BaseBindingViewHolder<ViewScheduleReceipt, MedicineIntakeScheduleItemBinding>(viewBinding) {

    override fun bind(item: ViewScheduleReceipt) = with(viewBinding) {
        val color  = when {
            bindingAdapterPosition   == 0 -> R.color.halfBlueCTA
            bindingAdapterPosition%2 == 0 -> R.color.skyBlue
            bindingAdapterPosition%3 == 0 -> R.color.light_sky
            bindingAdapterPosition%5 == 0 -> R.color.light_blue
            else -> R.color.creamBlue
        }
        cvLayout.setBackgroundColor(getColor(viewBinding.root.context, color))
        tvMedicineName.text = item.medicineName
        tvDate.text = item.date
        tvTime.text = item.time
    }
}