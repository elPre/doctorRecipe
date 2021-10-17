package com.recippie.doctor.app.holder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.recippie.doctor.app.adapter.CalculationProgramAdapter
import com.recippie.doctor.app.databinding.MedicineIntakeViewScheduleSaveBinding
import com.recippie.doctor.app.event.ReceiptActionEvent
import com.recippie.doctor.app.moduleitems.ModuleItemDataWrapper
import com.recippie.doctor.app.pojo.ViewScheduleProgram

class ViewScheduleReceiptViewHolder(
    val binding: MedicineIntakeViewScheduleSaveBinding,
    onAction: (ReceiptActionEvent) -> Unit,
) : ReceiptBaseViewHolder<ModuleItemDataWrapper<ViewScheduleProgram>, MedicineIntakeViewScheduleSaveBinding>(binding) {

    override val ivLoading = null

    private val adapter = CalculationProgramAdapter(onAction)

    init {
        with(binding.rvSchedule) {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ViewScheduleReceiptViewHolder.adapter
        }
        binding.btnSaveSchedule.setOnClickListener {
            onAction(ReceiptActionEvent.SaveProgram)
        }
    }

    override fun bind(item: ModuleItemDataWrapper<ViewScheduleProgram>) {
        with(item) {
            adapter.moduleData = data
            data.data.size.let {
                binding.tvDate.isVisible = it > 0
                binding.tvMedicineName.isVisible = it > 0
                binding.tvTime.isVisible = it > 0
                binding.btnSaveSchedule.isVisible = it > 0
            }
        }
    }
}