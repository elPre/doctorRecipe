package com.recippie.doctor.app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "currentalarmdata")
data class CurrentAlarmData(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val numAlarm: Int = 0,
    @ColumnInfo(name = "alarm") val alarm: Date,
    @ColumnInfo(name = "message") val message: String
)