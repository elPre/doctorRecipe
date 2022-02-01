package com.recippie.doctor.app.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.recippie.doctor.app.bo.BuildReceiptBO
import com.recippie.doctor.app.bo.IBuildReceiptBO
import com.recippie.doctor.app.pojo.*
import com.recippie.doctor.app.repository.AlarmRepository
import com.recippie.doctor.app.repository.ReceiptRepository
import kotlinx.coroutines.launch

class HistoryReceiptViewModel (val app: Application) : ViewModel() {

    private val receiptBo: IBuildReceiptBO = BuildReceiptBO(ReceiptRepository(app), AlarmRepository(app))
    val moduleItem = MutableLiveData<List<ReceiptModuleItem>>()

    fun loadHistoryReceipts() = viewModelScope.launch {
        val historyReceiptList = receiptBo.getHistoryAlarms().map {
            ViewScheduleProgram(ViewScheduleReceipt (
                medicineName = it.message,
                date = it.dateText,
                time = it.timeText
            ))
        }
        when {
            historyReceiptList.isNullOrEmpty().not() -> {
                val listItems =  mutableListOf<ReceiptModuleItem>()
                listItems.add(ObserveBannerHistoryTop)
                listItems.add(HeaderInfoList)
                listItems.addAll(historyReceiptList)
                moduleItem.postValue(listItems.toList())
            }
            else -> Unit
        }
    }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HistoryReceiptViewModel(app) as T
        }
    }
}