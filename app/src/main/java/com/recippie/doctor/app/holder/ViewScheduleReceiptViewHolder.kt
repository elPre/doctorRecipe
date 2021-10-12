package com.recippie.doctor.app.holder

import com.recippie.doctor.app.databinding.MedicineIntakeViewScheduleSaveBinding
import com.recippie.doctor.app.event.ReceiptActionEvent
import com.recippie.doctor.app.moduleitems.ModuleItemDataWrapper
import com.recippie.doctor.app.pojo.ViewScheduleProgram

class ViewScheduleReceiptViewHolder(
    val binding: MedicineIntakeViewScheduleSaveBinding,
    val onAction: (ReceiptActionEvent) -> Unit,
) : ReceiptBaseViewHolder<ModuleItemDataWrapper<ViewScheduleProgram>, MedicineIntakeViewScheduleSaveBinding>(binding) {

    override val ivLoading = null

    override fun bind(item: ModuleItemDataWrapper<ViewScheduleProgram>) = with(binding) {
        with(item.data) {

        }
    }
}