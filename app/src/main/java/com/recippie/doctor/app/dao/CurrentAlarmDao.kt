package com.recippie.doctor.app.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.recippie.doctor.app.data.CurrentAlarmData

@Dao
interface CurrentAlarmDao {

    @Insert
    suspend fun insertAlarm(vararg alarmData: CurrentAlarmData)

    @Query("DELETE FROM currentalarmdata")
    suspend fun deleteAllAlarms()

    @Query("SELECT * FROM currentalarmdata")
    suspend fun getAllCurrentAlarms(): List<CurrentAlarmData>

}