package com.recippie.doctor.app.bo

import com.recippie.doctor.app.pojo.Program
import com.recippie.doctor.app.pojo.Receipt
import java.util.Date

interface IBuildReceiptBO : CalculateDate, BuildAlarmsReceipt, GetCurrentReceipt, SaveReceipt

interface CalculateDate {
    suspend fun calculateDateAndTime(list: List<Receipt>): List<Program>
}

interface BuildAlarmsReceipt {
    suspend fun buildAlarmsForReceipt(dates: List<Date>, times: Int)
}

interface GetCurrentReceipt {
    suspend fun getCurrentReceipt(): List<Receipt>
}

interface SaveReceipt {
    suspend fun saveReceipt(list: List<Receipt>)
}