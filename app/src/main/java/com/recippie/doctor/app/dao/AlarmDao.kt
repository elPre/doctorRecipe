package com.recippie.doctor.app.dao

import androidx.room.*
import com.recippie.doctor.app.data.AlarmData

@Dao
interface AlarmDao {
    @Query("SELECT * FROM alarmdata WHERE num_receipt IN (:num_receipt)")
    suspend fun getAlarmsForReceipt(num_receipt: Long): List<AlarmData>

    @Query("SELECT * FROM alarmdata WHERE num_receipt IN (:num_receipt) AND alarm >= date('now')")
    suspend fun getAlarmsAvailableForReceipt(num_receipt: Long): List<AlarmData>

    @Query("SELECT * FROM alarmdata WHERE num_receipt < (:num_receipt)")
    suspend fun getHistoryAlarms(num_receipt: Long): List<AlarmData>

    @Insert
    suspend fun insertAlarm(vararg alarmData: AlarmData)

    @Delete
    suspend fun delete(alarmData: AlarmData)

    @Update
    suspend fun update(vararg alarmData: AlarmData)

    @Query("DELETE FROM alarmdata WHERE num_receipt = :num_receipt AND message = :message")
    suspend fun deleteSpecificAlarm(num_receipt: Long, message: String)

    @Query("SELECT * FROM alarmdata WHERE num_receipt = :num_receipt AND message = :message")
    suspend fun getSpecificAlarm(num_receipt: Long, message: String): List<AlarmData>
}