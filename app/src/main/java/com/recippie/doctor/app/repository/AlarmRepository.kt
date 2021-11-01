package com.recippie.doctor.app.repository

import com.recippie.doctor.app.dao.AlarmDao
import com.recippie.doctor.app.data.AlarmData

class AlarmRepository(private val alarmDao: AlarmDao): IAlarmRepository {

    override suspend fun getAllAlarms(currentReceipt: Int) = alarmDao.getAllAlarmsForReceipt(currentReceipt)

    override suspend fun insertAlarm(alarm: AlarmData) = alarmDao.insertAlarm(alarm)

    override suspend fun removeAlarm(alarm: AlarmData) = alarmDao.delete(alarm)
}