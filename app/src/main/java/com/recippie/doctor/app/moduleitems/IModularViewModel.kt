package com.recippie.doctor.app.moduleitems

import android.os.Build
import androidx.annotation.RequiresApi
import com.beachbody.bod.base.moduleitems.ModuleItemLoadingState
import com.beachbody.bod.base.moduleitems.isLoading
import com.recippie.doctor.app.util.isNegative
import com.recippie.doctor.app.util.isNotNegative

/**
 * Implement this interface in a viewmodel where you would like to have a modular
 * implementation. I.e.: Home Screen, Tracking My Progress
 */
interface IModularViewModel<ITEM_TYPE, MODULE_TYPE : IModuleItem<ITEM_TYPE>> {

    var moduleItems: MutableList<ModuleItemDataWrapper<MODULE_TYPE>>

    val isAnyModuleLoading
        get() = moduleItems.any { it.loadingState == ModuleItemLoadingState.LOADING }

    fun updateListAndPush(moduleItems: MODULE_TYPE, loadingState: ModuleItemLoadingState = ModuleItemLoadingState.LOADED, shouldAnimate: Boolean = true) {
        updateListAndPush(listOf(moduleItems), loadingState, shouldAnimate)
    }

    fun updateListAndPush(moduleItems: List<MODULE_TYPE>, loadingState: ModuleItemLoadingState = ModuleItemLoadingState.LOADED, shouldAnimate: Boolean = true) {
        moduleItems.forEach { moduleItem ->
            val index = this.moduleItems.indexOfFirst { it.data.itemType == moduleItem.itemType }
            when {
                index.isNegative -> this.moduleItems.addItem(moduleItem, loadingState = loadingState)
                else -> this.moduleItems[index] = ModuleItemDataWrapper(moduleItem, loadingState)
            }
        }

        this.moduleItems.sortBy { it.data.itemTypeOrdinal }
        pushModuleList(this.moduleItems, shouldAnimate)
    }

    fun setLoadingState(itemType: ITEM_TYPE) {
        if (!getItemLoadingState(itemType).isLoading) {
            getItem(itemType)?.let { moduleItem ->
                updateListAndPush(moduleItem.data, loadingState = ModuleItemLoadingState.LOADING)
            }
        }
    }

    fun getItem(itemType: ITEM_TYPE) = moduleItems.find { it.data.itemType == itemType }

    fun isItemLoading(itemType: ITEM_TYPE) = getItem(itemType)?.loadingState == ModuleItemLoadingState.LOADING

    fun isItemLoaded(itemType: ITEM_TYPE) = getItem(itemType)?.loadingState == ModuleItemLoadingState.LOADED

    @Suppress("UNCHECKED_CAST")
    fun <T : IModuleItem<ITEM_TYPE>> getItem(itemType: ITEM_TYPE) = moduleItems.find { it.data.itemType == itemType }?.data as? T

    private fun getItemLoadingState(itemType: ITEM_TYPE) = getItem(itemType)?.loadingState

    fun onRemoveModule(itemType: ITEM_TYPE, shouldPushModuleList: Boolean = true) {
        val index = moduleItems.indexOfFirst { it.data.itemType == itemType }
        if (index.isNotNegative) {
            moduleItems.removeAt(index)
            if (shouldPushModuleList) pushModuleList(moduleItems)
        }
    }

    fun containsItem(itemType: ITEM_TYPE): Boolean {
        return moduleItems.any { it.data.itemType == itemType }
    }

    fun replaceModule(previousModuleType: ITEM_TYPE, newModule: MODULE_TYPE) {
        val index = moduleItems.indexOfFirst { it.data.itemType == previousModuleType }
        if (index.isNotNegative) {
            onRemoveModule(previousModuleType, shouldPushModuleList = false)
            moduleItems.add(index, ModuleItemDataWrapper(newModule, ModuleItemLoadingState.LOADED))

            pushModuleList(moduleItems)
        } else {
            updateListAndPush(newModule)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun onRemoveModules(itemTypes: List<ITEM_TYPE>) {
        moduleItems.removeIf { itemTypes.contains(it.data.itemType) }
        pushModuleList(moduleItems)
    }

    fun <T> List<T>.replaceItem(newItem: T, predicate: (T) -> Boolean): MutableList<T> =
        toMutableList().apply {
            indexOfFirst(predicate)
                .takeIf { it.isNotNegative }
                ?.let { position ->
                    this[position] = newItem
                }
        }

    fun MutableList<ModuleItemDataWrapper<MODULE_TYPE>>.addItem(moduleItem: MODULE_TYPE, loadingState: ModuleItemLoadingState = ModuleItemLoadingState.LOADING) {
        add(ModuleItemDataWrapper(moduleItem, loadingState))
        sortBy { it.data.itemTypeOrdinal }
    }

    fun MODULE_TYPE.push(loadingState: ModuleItemLoadingState = ModuleItemLoadingState.LOADED, shouldAnimate: Boolean = true) {
        if (loadingState == ModuleItemLoadingState.LOADING && isItemLoading(itemType)) {
            return
        }

        updateListAndPush(this, loadingState, shouldAnimate = shouldAnimate)
    }

    fun List<MODULE_TYPE>.push(loadingState: ModuleItemLoadingState = ModuleItemLoadingState.LOADED) {
        updateListAndPush(this, loadingState)
    }

    fun pushModuleList(data: List<ModuleItemDataWrapper<MODULE_TYPE>>, shouldAnimate: Boolean = false)
}