package com.recippie.doctor.app.holder

import com.recippie.doctor.app.databinding.MedicineIntakeProgramItemBinding
import com.recippie.doctor.app.event.ReceiptActionEvent
import com.recippie.doctor.app.moduleitems.ModuleItemDataWrapper
import com.recippie.doctor.app.pojo.CreateProgram

class CreateProgramViewHolder(
    val binding: MedicineIntakeProgramItemBinding,
    val onAction: (ReceiptActionEvent) -> Unit,
) : ReceiptBaseViewHolder<ModuleItemDataWrapper<CreateProgram>, MedicineIntakeProgramItemBinding>(
    binding
) {

    override val ivLoading = null

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

    override fun bind(item: ModuleItemDataWrapper<CreateProgram>) = with(binding) {
        with(item.data.data) {
            etStartDate.setText(this?.dateSchedule)
            etStartTime.setText(this?.timeSchedule)
        }
    }
}