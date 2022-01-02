package com.recippie.doctor.app.holder

import androidx.core.content.ContextCompat
import com.recippie.doctor.app.R
import com.recippie.doctor.app.databinding.MedicineIntakeScheduleItemBinding
import com.recippie.doctor.app.event.CurrentHistoryActionEvent
import com.recippie.doctor.app.pojo.ViewScheduleReceipt

class CurrentAndHistoryInfoViewHolder(
    private val viewBinding: MedicineIntakeScheduleItemBinding,
    private val onAction: (CurrentHistoryActionEvent) -> Unit,
    private val getItem: (Int) -> ViewScheduleReceipt?
): BaseBindingViewHolder<ViewScheduleReceipt, MedicineIntakeScheduleItemBinding>(viewBinding) {

    override fun bind(item: ViewScheduleReceipt) = with(viewBinding) {
        val color  = when {
            bindingAdapterPosition   == 0 -> R.color.light_pink
            bindingAdapterPosition%2 == 0 -> R.color.light_sky
            bindingAdapterPosition%3 == 0 -> R.color.light_purple
            bindingAdapterPosition%5 == 0 -> R.color.light_blue
            else -> R.color.light_salmon
        }
        cvLayout.setBackgroundColor(ContextCompat.getColor(viewBinding.root.context, color))
        tvMedicineName.text = item.medicineName
        tvDate.text = item.date
        tvTime.text = item.time
    }
}