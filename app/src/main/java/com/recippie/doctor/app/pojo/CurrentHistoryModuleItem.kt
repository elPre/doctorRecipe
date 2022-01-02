package com.recippie.doctor.app.pojo

import com.recippie.doctor.app.moduleitems.IModuleItem

sealed class CurrentHistoryModuleItem(override val itemType: CurrentHistoryType) :
    IModuleItem<CurrentHistoryType> {
    override val itemTypeOrdinal: Int
        get() = itemType.ordinal
    val id: String = itemType.toString()
}

data class ReceiptInfo(val data: MutableList<ViewScheduleReceipt> = mutableListOf()) : CurrentHistoryModuleItem(CurrentHistoryType.INFO)
object EmptyData : CurrentHistoryModuleItem(CurrentHistoryType.EMPTY)

enum class CurrentHistoryType {
    EMPTY,
    INFO
}
