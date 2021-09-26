package com.recippie.doctor.app.pojo

import com.recippie.doctor.app.moduleitems.IModuleItem

sealed class RecipeModuleItem(override val itemType: RecipeItemType) :
    IModuleItem<RecipeItemType> {
        override val itemTypeOrdinal: Int
            get() = itemType.ordinal
        open val id: String = itemType.toString()
    }

data class RecipeInfo(val data:  List<Recipe>): RecipeModuleItem(RecipeItemType.RECIPE)

enum class RecipeItemType {
    RECIPE
//    HISTORY,
//    PROGRAM
}

data class Recipe(
    val description: String,
    val eachTime: String,
    val duringTime: String)