package com.recippie.doctor.app.repository

import android.app.Application
import com.recippie.doctor.app.data.AlarmData
import com.recippie.doctor.app.db.AppDataBase

class AlarmRepository(val app: Application): IAlarmRepository {

    private val alarmDao = AppDataBase.getInstance(app).alarmDao()

    override suspend fun getAlarms(currentReceipt: Long) = alarmDao.getAlarmsForReceipt(currentReceipt)

    override suspend fun getAlarmsAvailable(currentReceipt: Long) = alarmDao.getAlarmsAvailableForReceipt(currentReceipt)

    override suspend fun insertAlarm(alarms: List<AlarmData>) = alarmDao.insertAlarm(*alarms.toTypedArray())

    override suspend fun removeAlarm(alarm: AlarmData) = alarmDao.delete(alarm)

    override suspend fun updateAlarms(alarms: List<AlarmData>) = alarmDao.update(*alarms.toTypedArray())
}