package com.recippie.doctor.app.interfaces

import kotlinx.coroutines.Job

interface BaseProgram: LoadProgramPage, LoadProgram, LoadSchedule, CalculateDateTimes

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