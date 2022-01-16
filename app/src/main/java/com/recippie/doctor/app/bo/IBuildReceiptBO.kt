package com.recippie.doctor.app.bo

import com.recippie.doctor.app.data.AlarmData
import com.recippie.doctor.app.pojo.Program
import com.recippie.doctor.app.pojo.Receipt
import java.time.LocalDateTime
import java.util.Date

interface IBuildReceiptBO : CalculateDate,
    GetCurrentReceipt, SaveReceipt, DeleteReceipt,
    SaveProgram, GetCurrentAlarmList, HistoryAlarms

interface CalculateDate {
    suspend fun calculateDateAndTime(dateTime: LocalDateTime, list: List<Receipt>): List<Program>
}

interface GetCurrentReceipt {
    suspend fun getCurrentReceipt(): List<Receipt>
}

interface SaveReceipt {
    suspend fun saveReceipt(list: List<Receipt>)
}

interface DeleteReceipt {
    suspend fun deleteReceipt(receiptDelete: Receipt, alarmDelete: IAlarmActions)
}

interface SaveProgram {
    suspend fun saveProgram(list: List<Program>)
}

interface GetCurrentAlarmList {
    suspend fun getCurrentAlarmList() : List<AlarmData>
}

interface HistoryAlarms {
    suspend fun getHistoryAlarms() : List<AlarmData>
}