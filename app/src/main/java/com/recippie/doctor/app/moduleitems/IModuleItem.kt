package com.recippie.doctor.app.moduleitems

interface IModuleItem<ITEM_TYPE> {
    val itemType: ITEM_TYPE

    val itemTypeOrdinal: Int
}