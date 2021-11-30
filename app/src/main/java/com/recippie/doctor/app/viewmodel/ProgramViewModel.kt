package com.recippie.doctor.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.beachbody.bod.base.moduleitems.ModuleItemLoadingState
import com.beachbody.bod.base.moduleitems.isLoading
import com.google.android.material.timepicker.MaterialTimePicker
import com.recippie.doctor.app.bo.IBuildReceiptBO
import com.recippie.doctor.app.interfaces.BaseProgram
import com.recippie.doctor.app.moduleitems.IModularViewModel
import com.recippie.doctor.app.moduleitems.ModuleItemDataWrapper
import com.recippie.doctor.app.pojo.*
import com.recippie.doctor.app.util.immutable
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.*
import java.time.format.DateTimeFormatter

class ProgramViewModel(private val receiptBo: IBuildReceiptBO) : ViewModel(),
    IModularViewModel<ReceiptItemType, ReceiptModuleItem>,
    BaseProgram {

    private val _moduleItemsLiveData =
        MutableLiveData<List<ModuleItemDataWrapper<ReceiptModuleItem>>>()
    val moduleItemsLiveData = _moduleItemsLiveData.immutable

    private lateinit var date: LocalDate
    private lateinit var time: LocalTime
    private var dateS: String = ""
    private var timeS: String = ""
    private var receiptList: List<Receipt> = mutableListOf()

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

    override fun pushModuleList(
        data: List<ModuleItemDataWrapper<ReceiptModuleItem>>,
        shouldAnimate: Boolean
    ) {
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
        val dateTime = LocalDateTime.of(date, time)
        val list = receiptBo.calculateDateAndTime(dateTime, receiptList).map {
            ViewScheduleReceipt(it.medicine, it.date, it.time)
        }
        ViewScheduleProgram(list.toMutableList()).push(ModuleItemLoadingState.LOADED)
    }

    override fun calculateAlarmDateTimes() = viewModelScope.launch {

    }

    override fun setReceiptList(list: List<Receipt>) {
        receiptList = list
    }

    private fun updateDateAndTime() = viewModelScope.launch {
        setLoadingState(ReceiptItemType.INTAKE_PROGRAM_RECEIPT)
        CreateProgram(ProgramReceipt(timeS, dateS)).push(ModuleItemLoadingState.LOADED)
    }

    fun setDate(currentSelectedDate: Long) = viewModelScope.launch {
        val dateTime: LocalDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(currentSelectedDate),
            ZoneId.systemDefault()
        )
        val dateAsFormattedText: String = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        date = dateTime.toLocalDate()
        dateS = dateAsFormattedText
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
        time= LocalTime.of(timePicker.hour,timePicker.minute)
        timeS = selectedTime

        updateDateAndTime()
    }

    companion object {
        private val loadingItems: MutableList<ModuleItemDataWrapper<ReceiptModuleItem>>
            get() = mutableListOf(
                ModuleItemDataWrapper(CreateProgram(), ModuleItemLoadingState.LOADING),
                ModuleItemDataWrapper(ViewScheduleProgram(), ModuleItemLoadingState.LOADING)
            )
    }

    class Factory(private val receiptBO: IBuildReceiptBO) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProgramViewModel(receiptBO) as T
        }
    }

}