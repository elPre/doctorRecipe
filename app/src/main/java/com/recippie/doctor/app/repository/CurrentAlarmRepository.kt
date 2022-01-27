package com.recippie.doctor.app.repository

import android.app.Application
import com.recippie.doctor.app.data.CurrentAlarmData
import com.recippie.doctor.app.db.AppDataBase

class CurrentAlarmRepository(val app: Application): ICurrentAlarmRepository {

    private val currentAlarmDao = AppDataBase.getInstance(app).currentAlarmDao()

    override suspend fun getCurrentAlarms() = currentAlarmDao.getAllCurrentAlarms()

    override suspend fun saveCurrentAlarm(list: List<CurrentAlarmData>) = currentAlarmDao.insertAlarm(*list.toTypedArray())

    override suspend fun deleteAllAlarms() = currentAlarmDao.deleteAllAlarms()
}