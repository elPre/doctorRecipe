package com.recippie.doctor.app.pojo

sealed class ReceiptModuleItem(val itemType: ReceiptItemType)

object ProgramBannerTop : ReceiptModuleItem(ReceiptItemType.PROGRAM_BANNER_TOP)
object ProgramBannerBottom : ReceiptModuleItem(ReceiptItemType.PROGRAM_BANNER_BOTTOM)
data class CreateProgram(val data: ProgramReceipt?= null) : ReceiptModuleItem(ReceiptItemType.INTAKE_PROGRAM_RECEIPT)
data class ViewScheduleProgram(val data: ViewScheduleReceipt ?= null) : ReceiptModuleItem(ReceiptItemType.INTAKE_VIEW_PROGRAM)
object ProgramSaveBtn : ReceiptModuleItem(ReceiptItemType.INTAKE_SAVE_BTN)
object HeaderInfoList : ReceiptModuleItem(ReceiptItemType.HEADER_INFO_LIST)
object ObserveBannerCurrentTop : ReceiptModuleItem(ReceiptItemType.OBSERVE_BANNER_CURRENT)
object ObserveBannerHistoryTop : ReceiptModuleItem(ReceiptItemType.OBSERVE_BANNER_HISTORY)

enum class ReceiptItemType {
    PROGRAM_BANNER_TOP,
    PROGRAM_BANNER_BOTTOM,
    INTAKE_PROGRAM_RECEIPT,
    INTAKE_VIEW_PROGRAM,
    INTAKE_SAVE_BTN,
    HEADER_INFO_LIST,
    OBSERVE_BANNER_CURRENT,
    OBSERVE_BANNER_HISTORY,
}