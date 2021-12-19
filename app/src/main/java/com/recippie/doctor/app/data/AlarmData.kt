package com.recippie.doctor.app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "alarmdata")
data class AlarmData(
    @ColumnInfo(name = "num_alarm") @PrimaryKey (autoGenerate = true) val numAlarm: Int = 0,
    @ColumnInfo(name = "num_receipt") val numReceipt: Long,
    @ColumnInfo(name = "alarm") val alarm: Date,
    @ColumnInfo(name = "message") val message: String
)
