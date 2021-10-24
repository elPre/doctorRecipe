package com.recippie.doctor.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AlarmDao {
    @Query("SELECT * FROM alarmdata WHERE num_receipt IN (:num_receipt)")
    fun alarmSet(num_receipt: Int): List<AlarmData>

    @Insert
    fun insertAlarm(vararg alarmData: AlarmData)

    @Delete
    fun delete(alarmData: AlarmData)
}