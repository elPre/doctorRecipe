package com.recippie.doctor.app.repository

import android.app.Application
import com.recippie.doctor.app.data.AlarmData
import com.recippie.doctor.app.db.AppDataBase

class AlarmRepository(val app: Application): IAlarmRepository {

    private val alarmDao = AppDataBase.getInstance(app).alarmDao()

    override suspend fun getAlarms(currentReceipt: Int) = alarmDao.getAlarmsForReceipt(currentReceipt)

    override suspend fun getAlarmsAvailable(currentReceipt: Int) = alarmDao.getAlarmsAvailableForReceipt(currentReceipt)

    override suspend fun insertAlarm(alarmList: List<AlarmData>) = alarmDao.insertAlarm(*alarmList.toTypedArray())

    override suspend fun removeAlarm(alarm: AlarmData) = alarmDao.delete(alarm)
}