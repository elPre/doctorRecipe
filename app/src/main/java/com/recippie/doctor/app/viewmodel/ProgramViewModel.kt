package com.recippie.doctor.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beachbody.bod.base.moduleitems.ModuleItemLoadingState
import com.beachbody.bod.base.moduleitems.isLoading
import com.recippie.doctor.app.interfaces.BaseProgram
import com.recippie.doctor.app.moduleitems.IModularViewModel
import com.recippie.doctor.app.moduleitems.ModuleItemDataWrapper
import com.recippie.doctor.app.pojo.CreateProgram
import com.recippie.doctor.app.pojo.ProgramReceipt
import com.recippie.doctor.app.pojo.ReceiptItemType
import com.recippie.doctor.app.pojo.ReceiptModuleItem
import com.recippie.doctor.app.pojo.ViewScheduleProgram
import com.recippie.doctor.app.pojo.ViewScheduleReceipt
import com.recippie.doctor.app.util.immutable
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ProgramViewModel : ViewModel(), IModularViewModel<ReceiptItemType, ReceiptModuleItem>, BaseProgram {

    private val _moduleItemsLiveData = MutableLiveData<List<ModuleItemDataWrapper<ReceiptModuleItem>>>()
    val moduleItemsLiveData = _moduleItemsLiveData.immutable

    override var moduleItems= mutableListOf<ModuleItemDataWrapper<ReceiptModuleItem>>()
        set(value) {
            field = value
            _moduleItemsLiveData.value = value
        }

    init {
        if(moduleItems.isEmpty()){
            moduleItems =  loadingItems
        }
    }


    override fun pushModuleList(data: List<ModuleItemDataWrapper<ReceiptModuleItem>>, shouldAnimate: Boolean) {
        _moduleItemsLiveData.postValue(data)
    }

    override fun loadPage(forceReload: Boolean)= viewModelScope.launch {
        if(!forceReload) {
            val isAnyModuleLoading = moduleItems.any() {it.loadingState.isLoading}
            if(!isAnyModuleLoading && moduleItems.isNotEmpty()) {
                _moduleItemsLiveData.postValue(moduleItems)
                return@launch
            }
        }
        async { loadProgram() }
    }

    override fun loadProgram()= viewModelScope.launch {
        CreateProgram(ProgramReceipt("15:09","09/10/2021")).push(ModuleItemLoadingState.LOADED)
    }

    override fun loadSchedule()= viewModelScope.launch {
        setLoadingState(ReceiptItemType.INTAKE_VIEW_PROGRAM)
        val list: MutableList<ViewScheduleReceipt> = mutableListOf()
        for (i in 1..20) {
            list.add(ViewScheduleReceipt("medicine $i", "date $i", "time $i"))
        }
        ViewScheduleProgram(list).push(ModuleItemLoadingState.LOADED)
    }

    companion object {
        private val loadingItems: MutableList<ModuleItemDataWrapper<ReceiptModuleItem>>
            get() = mutableListOf(
                ModuleItemDataWrapper(CreateProgram(), ModuleItemLoadingState.LOADING),
                ModuleItemDataWrapper(ViewScheduleProgram(), ModuleItemLoadingState.LOADING)
            )
    }
}