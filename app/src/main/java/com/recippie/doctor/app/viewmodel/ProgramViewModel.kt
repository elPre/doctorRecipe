package com.recippie.doctor.app.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.beachbody.bod.base.moduleitems.ModuleItemLoadingState
import com.beachbody.bod.base.moduleitems.isLoading
import com.google.android.material.timepicker.MaterialTimePicker
import com.recippie.doctor.app.bo.AlarmBO
import com.recippie.doctor.app.bo.BuildReceiptBO
import com.recippie.doctor.app.bo.IAlarmActions
import com.recippie.doctor.app.bo.IBuildReceiptBO
import com.recippie.doctor.app.interfaces.BaseProgram
import com.recippie.doctor.app.moduleitems.IModularViewModel
import com.recippie.doctor.app.moduleitems.ModuleItemDataWrapper
import com.recippie.doctor.app.pojo.*
import com.recippie.doctor.app.repository.AlarmRepository
import com.recippie.doctor.app.repository.IAlarmRepository
import com.recippie.doctor.app.repository.IReceiptRepository
import com.recippie.doctor.app.repository.ReceiptRepository
import com.recippie.doctor.app.util.SingleLiveEvent
import com.recippie.doctor.app.util.immutable
import kotlinx.coroutines.launch
import java.time.*
import java.time.format.DateTimeFormatter

class ProgramViewModel(val app: Application) : ViewModel(),
    IModularViewModel<ReceiptItemType, ReceiptModuleItem>,
    BaseProgram {

    private val receiptBo: IBuildReceiptBO = BuildReceiptBO(ReceiptRepository(app), AlarmRepository(app))
    private val alarmBo: IAlarmActions = AlarmBO(app)
    private val _moduleItemsLiveData = MutableLiveData<List<ModuleItemDataWrapper<ReceiptModuleItem>>>()
    val moduleItemsLiveData = _moduleItemsLiveData.immutable

    private lateinit var date: LocalDate
    private lateinit var time: LocalTime
    private var dateS: String = ""
    private var timeS: String = ""
    private var receiptList: List<Receipt> = mutableListOf()
    private var programList = listOf<Program>()
    val communicatToUserKnowledge: MutableLiveData<Boolean> = SingleLiveEvent()

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
        loadProgram()
    }

    override fun loadProgram() = viewModelScope.launch {
        receiptList = receiptBo.getCurrentReceipt()
        CreateProgram(ProgramReceipt()).push(ModuleItemLoadingState.LOADED)
    }

    override fun loadSchedule() = viewModelScope.launch {
        val dateTime = LocalDateTime.of(date, time)
        programList = receiptBo.calculateDateAndTime(dateTime, receiptList)
        val list = programList.map {
            ViewScheduleReceipt(it.medicine, it.date, it.time)
        }
        ViewScheduleProgram(list.toMutableList()).push(ModuleItemLoadingState.LOADED)
    }

    override fun saveProgram() = viewModelScope.launch {
        if(programList.isNotEmpty()) {
            receiptBo.saveProgram(programList)
            //set the alarm manager method
            alarmBo.buildAlarm()
            communicatToUserKnowledge.postValue(true)
        }
    }

    private fun updateDateAndTime() = viewModelScope.launch {
        setLoadingState(ReceiptItemType.INTAKE_PROGRAM_RECEIPT)
        CreateProgram(ProgramReceipt(timeS, dateS)).push(ModuleItemLoadingState.LOADED)
    }

    fun setDate(currentSelectedDate: Long) = viewModelScope.launch {
        val dateTime: LocalDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli((currentSelectedDate+(24*60*60*1000))),
            ZoneId.systemDefault()
        )
        val dateAsFormattedText: String = dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
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

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProgramViewModel(app) as T
        }
    }
}