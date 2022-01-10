package com.recippie.doctor.app.pojo

import com.recippie.doctor.app.moduleitems.IModuleItem

sealed class HistoryModuleItem(override val itemType: HistoryType) :
    IModuleItem<HistoryType> {
    override val itemTypeOrdinal: Int
        get() = itemType.ordinal
    val id: String = itemType.toString()
}

object HeaderData : HistoryModuleItem(HistoryType.HEADER)
data class HistoryInfo(val data: MutableList<ViewScheduleReceipt> = mutableListOf()) : HistoryModuleItem(HistoryType.HISTORY_INFO)

enum class HistoryType {
    HEADER,
    HISTORY_INFO
}
