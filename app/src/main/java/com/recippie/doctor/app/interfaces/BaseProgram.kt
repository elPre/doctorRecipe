package com.recippie.doctor.app.interfaces

import com.recippie.doctor.app.pojo.Receipt
import kotlinx.coroutines.Job

interface BaseProgram: LoadProgramPage, LoadProgram, LoadSchedule, CalculateDateTimes, SetReceiptList

interface LoadProgramPage {
    fun loadPage(forceReload: Boolean = true): Job
}

interface LoadProgram {
    fun loadProgram(): Job
}

interface LoadSchedule {
    fun loadSchedule(): Job
}

interface CalculateDateTimes {
    fun calculateAlarmDateTimes(): Job
}

interface SetReceiptList {
    fun setReceiptList(list: List<Receipt>)
}