package com.recippie.doctor.app.pojo

import com.recippie.doctor.app.moduleitems.IModuleItem

sealed class ReceiptModuleItem(override val itemType: ReceiptItemType) :
    IModuleItem<ReceiptItemType> {
        override val itemTypeOrdinal: Int
            get() = itemType.ordinal
        open val id: String = itemType.toString()
    }

data class CreateProgram(val data: ProgramReceipt): ReceiptModuleItem(ReceiptItemType.INTAKE_PROGRAM_RECEIPT)
data class ViewScheduleProgram(val data: List<ViewScheduleReceipt> = emptyList()): ReceiptModuleItem(ReceiptItemType.INTAKE_VIEW_PROGRAM)

enum class ReceiptItemType {
    INTAKE_PROGRAM_RECEIPT,
    INTAKE_VIEW_PROGRAM
}

data class Receipt(
    val description: String,
    val eachTime: String,
    val duringTime: String)

data class ProgramReceipt(
    val timeSchedule: String,
    val dateSchedule: String
)

data class ViewScheduleReceipt(
    val medicineName: String,
    val date: String,
    val time: String
)