package com.recippie.doctor.app.repository

import com.recippie.doctor.app.data.AlarmData

interface IAlarmRepository: GetAlarm, InsertAlarm, RemoveAlarm

interface GetAlarm {
    suspend fun getAllAlarms(currentReceipt: Int): List<AlarmData>
}

interface InsertAlarm {
    suspend fun insertAlarm(alarm: AlarmData)
}

interface RemoveAlarm {
    suspend fun removeAlarm(alarm: AlarmData)
}