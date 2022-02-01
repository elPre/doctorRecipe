package com.recippie.doctor.app.holder

import com.recippie.doctor.app.databinding.ProgramSaveBtnBinding
import com.recippie.doctor.app.event.ReceiptActionEvent
import com.recippie.doctor.app.pojo.ProgramSaveBtn

class ProgramSaveViewHolder(
    private val viewBinding: ProgramSaveBtnBinding,
    private val onAction: (ReceiptActionEvent) -> Unit,
): BaseProgramViewHolder<ProgramSaveBtn>(viewBinding.root) {
    init {
        viewBinding.btnSaveSchedule.setOnClickListener {
            onAction(ReceiptActionEvent.SaveProgram)
        }
    }
    override fun bind(item: ProgramSaveBtn) { }
}