package com.recippie.doctor.app.holder

import com.recippie.doctor.app.databinding.MedicineIntakeProgramItemBinding
import com.recippie.doctor.app.event.ReceiptActionEvent
import com.recippie.doctor.app.pojo.CreateProgram

class CreateProgramViewHolder(
    val binding: MedicineIntakeProgramItemBinding,
    val onAction: (ReceiptActionEvent) -> Unit,
) : BaseProgramViewHolder<CreateProgram>(binding.root) {

    init {
        with(binding) {
            etStartDate.setOnClickListener {
                onAction(ReceiptActionEvent.OpenCalendar)
            }
            etStartTime.setOnClickListener {
                onAction(ReceiptActionEvent.OpenClock)
            }
            btnProgram.setOnClickListener {
                when {
                    etStartDate.text.toString().isEmpty() || etStartTime.text.toString().isEmpty() -> {
                        onAction(ReceiptActionEvent.NotEmptyFieldsAllowed)
                        return@setOnClickListener
                    }
                }
                onAction(ReceiptActionEvent.ProgramSchedule)
            }
        }
    }

    override fun bind(item: CreateProgram) = with(binding) {
        with(item.data) {
            etStartDate.setText(this?.dateSchedule)
            etStartTime.setText(this?.timeSchedule)
        }
    }
}