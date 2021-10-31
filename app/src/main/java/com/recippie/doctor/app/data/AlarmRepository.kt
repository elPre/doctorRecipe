package com.recippie.doctor.app.data

class AlarmRepository(private val alarmDao: AlarmDao) {

    suspend fun getAlarm(currentReceipt: Int) = alarmDao.alarmSet(currentReceipt)

    suspend fun insertAlarm(alarm: AlarmData) {
        alarmDao.insertAlarm(alarm)
    }

    suspend fun removeAlarm(alarm: AlarmData) {
        alarmDao.delete(alarm)
    }

}