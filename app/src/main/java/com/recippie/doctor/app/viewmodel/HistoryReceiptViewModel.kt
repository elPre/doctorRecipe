package com.recippie.doctor.app.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.beachbody.bod.base.moduleitems.ModuleItemLoadingState
import com.recippie.doctor.app.bo.BuildReceiptBO
import com.recippie.doctor.app.bo.IBuildReceiptBO
import com.recippie.doctor.app.moduleitems.IModularViewModel
import com.recippie.doctor.app.moduleitems.ModuleItemDataWrapper
import com.recippie.doctor.app.pojo.*
import com.recippie.doctor.app.repository.AlarmRepository
import com.recippie.doctor.app.repository.ReceiptRepository
import com.recippie.doctor.app.util.immutable
import kotlinx.coroutines.launch

class HistoryReceiptViewModel (val app: Application) : ViewModel(),
    IModularViewModel<HistoryType, HistoryModuleItem> {

    private val receiptBo: IBuildReceiptBO = BuildReceiptBO(ReceiptRepository(app), AlarmRepository(app))
    private val _moduleItemsLiveData = MutableLiveData<List<ModuleItemDataWrapper<HistoryModuleItem>>>()
    val moduleItemsLiveData = _moduleItemsLiveData.immutable

    override var moduleItems = mutableListOf<ModuleItemDataWrapper<HistoryModuleItem>>()
        set(value) {
            field = value
            _moduleItemsLiveData.value = value
        }

    init {
        if (moduleItems.isEmpty()) {
            moduleItems = loadingItems
        }
    }

    override fun pushModuleList(
        data: List<ModuleItemDataWrapper<HistoryModuleItem>>,
        shouldAnimate: Boolean
    ) {
        _moduleItemsLiveData.postValue(data)
    }


    fun loadHistoryReceipts() = viewModelScope.launch {
        val historyReceiptList = receiptBo.getHistoryAlarms().map {
            ViewScheduleReceipt (
                medicineName = it.message,
                date = it.dateText,
                time = it.timeText
            )
        }
        when {
            historyReceiptList.isNullOrEmpty().not() -> {
                HeaderData.push(ModuleItemLoadingState.LOADED)
                HistoryInfo(historyReceiptList.toMutableList()).push(ModuleItemLoadingState.LOADED)
            }
            else -> Unit
        }
    }

    companion object {
        private val loadingItems: MutableList<ModuleItemDataWrapper<HistoryModuleItem>>
            get() = mutableListOf(
                ModuleItemDataWrapper( HistoryBanner, ModuleItemLoadingState.LOADING),
                ModuleItemDataWrapper(HeaderData , ModuleItemLoadingState.LOADING),
                ModuleItemDataWrapper(HistoryInfo(), ModuleItemLoadingState.LOADING)
            )
    }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HistoryReceiptViewModel(app) as T
        }
    }
}