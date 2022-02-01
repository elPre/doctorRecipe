package com.recippie.doctor.app.holder

import androidx.core.content.ContextCompat.getColor
import com.recippie.doctor.app.R
import com.recippie.doctor.app.databinding.MedicineIntakeScheduleItemBinding
import com.recippie.doctor.app.event.ReceiptActionEvent
import com.recippie.doctor.app.pojo.ViewScheduleProgram

class ViewScheduleReceiptItemViewHolder(
    private val viewBinding: MedicineIntakeScheduleItemBinding,
    private val onAction: (ReceiptActionEvent) -> Unit,
): BaseProgramViewHolder<ViewScheduleProgram>(viewBinding.root) {

    override fun bind(item: ViewScheduleProgram) = with(viewBinding) {
        val color  = when {
            bindingAdapterPosition%2 == 0 -> R.color.light_sky
            bindingAdapterPosition%3 == 0 -> R.color.light_purple
            else -> R.color.light_salmon
        }
        cvLayout.setBackgroundColor(getColor(viewBinding.root.context, color))
        tvMedicineName.text = item.data?.medicineName
        tvDate.text = item.data?.date
        tvTime.text = item.data?.time
    }
}