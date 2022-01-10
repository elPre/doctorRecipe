package com.recippie.doctor.app.repository

import com.recippie.doctor.app.data.AlarmData

interface IAlarmRepository: GetAlarm, GetAlarmsAvailable,
    InsertAlarm, RemoveAlarm, UpdateAlarm, HistoryAlarms,
    DeleteSpecificAlarm, GetSpecificAlarm

interface GetAlarm {
    suspend fun getAlarms(currentReceipt: Long): List<AlarmData>
}

interface GetAlarmsAvailable {
    suspend fun getAlarmsAvailable(currentReceipt: Long): List<AlarmData>
}

interface InsertAlarm {
    suspend fun insertAlarm(alarms: List<AlarmData>)
}

interface RemoveAlarm {
    suspend fun removeAlarm(alarm: AlarmData)
}

interface UpdateAlarm {
    suspend fun updateAlarms(alarms: List<AlarmData>)
}

interface HistoryAlarms {
    suspend fun getAllAlarms(): List<AlarmData>
}

interface DeleteSpecificAlarm {
    suspend fun deleteSpecificAlarm(alarm: AlarmData)
}

interface GetSpecificAlarm {
    suspend fun getSpecificAlarm(alarm: AlarmData) : List<AlarmData>
}