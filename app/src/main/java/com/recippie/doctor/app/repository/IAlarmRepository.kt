package com.recippie.doctor.app.repository

import com.recippie.doctor.app.data.AlarmData

interface IAlarmRepository: GetAlarm, GetAlarmsAvailable, InsertAlarm, RemoveAlarm

interface GetAlarm {
    suspend fun getAlarms(currentReceipt: Int): List<AlarmData>
}

interface GetAlarmsAvailable {
    suspend fun getAlarmsAvailable(currentReceipt: Int): List<AlarmData>
}

interface InsertAlarm {
    suspend fun insertAlarm(alarms: List<AlarmData>)
}

interface RemoveAlarm {
    suspend fun removeAlarm(alarms: AlarmData)
}