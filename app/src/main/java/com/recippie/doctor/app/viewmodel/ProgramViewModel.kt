package com.recippie.doctor.app.viewmodel

import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beachbody.bod.base.moduleitems.ModuleItemLoadingState
import com.beachbody.bod.base.moduleitems.isLoading
import com.google.android.material.timepicker.MaterialTimePicker
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
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ProgramViewModel : ViewModel(), IModularViewModel<ReceiptItemType, ReceiptModuleItem>, BaseProgram {

    private val _moduleItemsLiveData = MutableLiveData<List<ModuleItemDataWrapper<ReceiptModuleItem>>>()
    val moduleItemsLiveData = _moduleItemsLiveData.immutable

    var dataList: MutableList<ViewScheduleReceipt> = mutableListOf()

    private var date: String = ""
    private var time: String = ""

    override var moduleItems = mutableListOf<ModuleItemDataWrapper<ReceiptModuleItem>>()
        set(value) {
            field = value
            _moduleItemsLiveData.value = value
        }

    init {
        if (moduleItems.isEmpty()) {
            moduleItems = loadingItems
        }
    }


    override fun pushModuleList(data: List<ModuleItemDataWrapper<ReceiptModuleItem>>, shouldAnimate: Boolean) {
        _moduleItemsLiveData.postValue(data)
    }

    override fun loadPage(forceReload: Boolean) = viewModelScope.launch {
        if (!forceReload) {
            val isAnyModuleLoading = moduleItems.any { it.loadingState.isLoading }
            if (!isAnyModuleLoading && moduleItems.isNotEmpty()) {
                _moduleItemsLiveData.postValue(moduleItems)
                return@launch
            }
        }
        async { loadProgram() }
    }

    override fun loadProgram() = viewModelScope.launch {
        CreateProgram(ProgramReceipt()).push(ModuleItemLoadingState.LOADED)
    }

    override fun loadSchedule() = viewModelScope.launch {
        val list: MutableList<ViewScheduleReceipt> = mutableListOf()
        for (i in 1..10) {
            list.add(ViewScheduleReceipt("medicine $i", "date $i", "time $i"))
        }
        ViewScheduleProgram(list).push(ModuleItemLoadingState.LOADED)
    }

    private fun updateDateAndTime() = viewModelScope.launch {
        setLoadingState(ReceiptItemType.INTAKE_PROGRAM_RECEIPT)
        CreateProgram(ProgramReceipt(time, date)).push(ModuleItemLoadingState.LOADED)
    }

    fun setDate(currentSelectedDate: Long) = viewModelScope.launch {
        date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val dateTime: LocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(currentSelectedDate), ZoneId.systemDefault())
            val dateAsFormattedText: String = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            dateAsFormattedText
        } else {
            ""
        }
        updateDateAndTime()
    }

    fun setTime(timePicker: MaterialTimePicker) = viewModelScope.launch {
        val selectedTime = if (timePicker.hour > 12) {
            String.format("%02d", timePicker.hour - 12) + " : " +
                    String.format("%02d", timePicker.minute) + " PM"
        } else {
            String.format("%02d", timePicker.hour) + " : " +
                    String.format("%02d", timePicker.minute) + " AM"
        }
        time = selectedTime
        updateDateAndTime()
    }

    companion object {
        private val loadingItems: MutableList<ModuleItemDataWrapper<ReceiptModuleItem>>
            get() = mutableListOf(
                ModuleItemDataWrapper(CreateProgram(), ModuleItemLoadingState.LOADING),
                ModuleItemDataWrapper(ViewScheduleProgram(), ModuleItemLoadingState.LOADING)
            )
    }
}