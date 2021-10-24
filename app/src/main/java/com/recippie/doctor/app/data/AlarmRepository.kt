package com.recippie.doctor.app.data

import androidx.annotation.WorkerThread

class AlarmRepository(private val alarmDao: AlarmDao) {

    fun getAlarm(currentReceipt: Int) = alarmDao.alarmSet(currentReceipt)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAlarm(alarm: AlarmData) {
        alarmDao.insertAlarm(alarm)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun removeAlarm(alarm: AlarmData) {
        alarmDao.delete(alarm)
    }

}