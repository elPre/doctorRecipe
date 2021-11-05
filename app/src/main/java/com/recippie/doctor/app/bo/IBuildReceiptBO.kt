package com.recippie.doctor.app.bo

import com.recippie.doctor.app.pojo.Receipt
import java.util.Date

interface IBuildReceiptBO: CalculateTimes, CalculateDate, BuildAlarmsReceipt, GetCurrentReceipt, SaveReceipt

interface CalculateTimes {
    suspend fun calculateTimes(): Int
}

interface CalculateDate {
    suspend fun calculateDates(): List<Date>
}

interface BuildAlarmsReceipt {
    suspend fun buildAlarmsForReceipt(dates: List<Date>, times: Int)
}

interface GetCurrentReceipt {
    suspend fun getCurrentReceipt(): List<Receipt>
}

interface SaveReceipt {
    suspend fun saveReceipt(list:MutableList<Receipt>)
}