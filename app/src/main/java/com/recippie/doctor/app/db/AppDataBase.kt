package com.recippie.doctor.app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.recippie.doctor.app.dao.AlarmDao
import com.recippie.doctor.app.data.AlarmData
import com.recippie.doctor.app.data.Converters
import com.recippie.doctor.app.dao.ReceiptDao
import com.recippie.doctor.app.data.ReceiptData

@Database(entities = [ReceiptData::class, AlarmData::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun receiptDao(): ReceiptDao
    abstract fun alarmDao(): AlarmDao

    companion object {
        private const val NAME = "app_database"
        @Synchronized
        fun getInstance(appContext: Context): AppDataBase {
            return Room.databaseBuilder(
                appContext,
                AppDataBase::class.java,
                NAME)
                .fallbackToDestructiveMigration()
                .build()

        }
    }
}
