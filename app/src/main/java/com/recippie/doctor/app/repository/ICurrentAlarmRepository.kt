package com.recippie.doctor.app.repository

import com.recippie.doctor.app.data.CurrentAlarmData

interface ICurrentAlarmRepository : GetCurrentAlarm, SaveCurrentAlarm, DeleteCurrentAlarm

interface GetCurrentAlarm {
    suspend fun getCurrentAlarms(): List<CurrentAlarmData>
}

interface SaveCurrentAlarm {
    suspend fun saveCurrentAlarm(list: List<CurrentAlarmData>)
}

interface DeleteCurrentAlarm {
    suspend fun deleteAllAlarms()
}