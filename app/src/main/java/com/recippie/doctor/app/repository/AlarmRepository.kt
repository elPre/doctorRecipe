package com.recippie.doctor.app.repository

import com.recippie.doctor.app.dao.AlarmDao
import com.recippie.doctor.app.data.AlarmData

class AlarmRepository(private val alarmDao: AlarmDao): IAlarmRepository {

    override suspend fun getAlarms(currentReceipt: Int) = alarmDao.getAlarmsForReceipt(currentReceipt)

    override suspend fun getAlarmsAvailable(currentReceipt: Int) = alarmDao.getAlarmsAvailableForReceipt(currentReceipt)

    override suspend fun insertAlarm(alarm: AlarmData) = alarmDao.insertAlarm(alarm)

    override suspend fun removeAlarm(alarm: AlarmData) = alarmDao.delete(alarm)
}