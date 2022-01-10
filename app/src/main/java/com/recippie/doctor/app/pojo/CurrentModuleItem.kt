package com.recippie.doctor.app.pojo

import com.recippie.doctor.app.moduleitems.IModuleItem

sealed class CurrentModuleItem(override val itemType: CurrentType) :
    IModuleItem<CurrentType> {
    override val itemTypeOrdinal: Int
        get() = itemType.ordinal
    val id: String = itemType.toString()
}

data class ReceiptInfo(val data: MutableList<ViewScheduleReceipt> = mutableListOf()) : CurrentModuleItem(CurrentType.INFO)
object EmptyData : CurrentModuleItem(CurrentType.EMPTY)

enum class CurrentType {
    EMPTY,
    INFO
}
