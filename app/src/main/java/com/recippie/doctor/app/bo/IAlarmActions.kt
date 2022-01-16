package com.recippie.doctor.app.bo

import com.recippie.doctor.app.data.AlarmData

interface IAlarmActions: BuildAlarm, DeleteAlarm

interface BuildAlarm {
    suspend fun buildAlarm()
}

interface DeleteAlarm {
    suspend fun deleteAlarms(listToDelete: List<AlarmData>)
}