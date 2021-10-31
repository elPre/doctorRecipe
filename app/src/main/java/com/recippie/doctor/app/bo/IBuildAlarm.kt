package com.recippie.doctor.app.bo

import java.util.Date

interface IBuildAlarm: CalculateTimes, CalculateDate, BuildAlarmsReceipt

interface CalculateTimes {
    fun calculateTimes(): Int
}

interface CalculateDate {
    fun calculateDates(): List<Date>
}

interface BuildAlarmsReceipt {
    fun buildAlarmsForReceipt(dates: List<Date>, times: Int)
}