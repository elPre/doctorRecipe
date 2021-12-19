package com.recippie.doctor.app.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.recippie.doctor.app.data.AlarmData

@Dao
interface AlarmDao {
    @Query("SELECT * FROM alarmdata WHERE num_receipt IN (:num_receipt)")
    suspend fun getAlarmsForReceipt(num_receipt: Int): List<AlarmData>

    @Query("SELECT * FROM alarmdata WHERE num_receipt IN (:num_receipt) AND alarm >= date('now')")
    suspend fun getAlarmsAvailableForReceipt(num_receipt: Int): List<AlarmData>

    @Insert
    suspend fun insertAlarm(vararg alarmData: AlarmData)

    @Delete
    suspend fun delete(alarmData: AlarmData)
}