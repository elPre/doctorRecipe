package com.recippie.doctor.app.pojo

import com.recippie.doctor.app.moduleitems.IModuleItem

sealed class ReceiptModuleItem(override val itemType: ReceiptItemType) :
    IModuleItem<ReceiptItemType> {
        override val itemTypeOrdinal: Int
            get() = itemType.ordinal
        open val id: String = itemType.toString()
    }

data class CreateProgram(val data: ProgramReceipt?= null): ReceiptModuleItem(ReceiptItemType.INTAKE_PROGRAM_RECEIPT)
data class ViewScheduleProgram(val data: MutableList<ViewScheduleReceipt> = mutableListOf()): ReceiptModuleItem(ReceiptItemType.INTAKE_VIEW_PROGRAM)

enum class ReceiptItemType {
    INTAKE_PROGRAM_RECEIPT,
    INTAKE_VIEW_PROGRAM
}