package com.recippie.doctor.app.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.google.android.material.timepicker.MaterialTimePicker
import com.recippie.doctor.app.bo.AlarmBO
import com.recippie.doctor.app.bo.BuildReceiptBO
import com.recippie.doctor.app.bo.IAlarmActions
import com.recippie.doctor.app.bo.IBuildReceiptBO
import com.recippie.doctor.app.interfaces.BaseProgram
import com.recippie.doctor.app.pojo.*
import com.recippie.doctor.app.repository.AlarmRepository
import com.recippie.doctor.app.repository.ReceiptRepository
import com.recippie.doctor.app.util.SingleLiveEvent
import kotlinx.coroutines.launch
import java.time.*
import java.time.format.DateTimeFormatter

class ProgramViewModel(val app: Application) : ViewModel(), BaseProgram {

    private val receiptBo: IBuildReceiptBO = BuildReceiptBO(ReceiptRepository(app), AlarmRepository(app))
    private val alarmBo: IAlarmActions = AlarmBO(app)

    private lateinit var date: LocalDate
    private lateinit var time: LocalTime
    private var dateS: String = ""
    private var timeS: String = ""
    private var receiptList: List<Receipt> = mutableListOf()
    private var programList = listOf<Program>()
    val communicatToUserKnowledge: MutableLiveData<Boolean> = SingleLiveEvent()
    val constrainsDateTime: MutableLiveData<Boolean> = SingleLiveEvent()
    val moduleItem = MutableLiveData<List<ReceiptModuleItem>>()

    override fun loadPage(forceReload: Boolean) = viewModelScope.launch {
        loadProgram()
    }

    override fun loadProgram() = viewModelScope.launch {
        receiptList = receiptBo.getCurrentReceipt()
        val list =  mutableListOf<ReceiptModuleItem>()
        list.add(ProgramBannerTop)
        list.add(CreateProgram())
        moduleItem.postValue(list.toList())
    }

    override fun loadSchedule() = viewModelScope.launch {
        val dateTime = LocalDateTime.of(date, time)
        if (dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() < System.currentTimeMillis()) {
            constrainsDateTime.postValue(true)
            return@launch
        }
        programList = receiptBo.calculateDateAndTime(dateTime, receiptList)
        val list = programList.map {
            ViewScheduleProgram(ViewScheduleReceipt(it.medicine, it.date, it.time))
        }
        val listItems =  mutableListOf<ReceiptModuleItem>()
        listItems.add(ProgramBannerTop)
        listItems.add((CreateProgram(ProgramReceipt(timeS, dateS))))
        listItems.add(HeaderInfoList)
        listItems.addAll(list)
        listItems.add(ProgramSaveBtn)
        listItems.add(ProgramBannerBottom)
        moduleItem.postValue(listItems.toList())
    }

    override fun saveProgram() = viewModelScope.launch {
        if(programList.isNotEmpty()) {
            receiptBo.saveProgram(programList)
            alarmBo.buildAlarm()
            communicatToUserKnowledge.postValue(true)
        }
    }

    private fun updateDateAndTime() = viewModelScope.launch {
        val list =  mutableListOf<ReceiptModuleItem>()
        list.add(ProgramBannerTop)
        list.add(CreateProgram(ProgramReceipt(timeS, dateS)))
        moduleItem.postValue(list.toList())
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

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProgramViewModel(app) as T
        }
    }
}