package com.recippie.doctor.app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.recippie.doctor.app.data.AlarmDao
import com.recippie.doctor.app.data.AlarmData
import com.recippie.doctor.app.data.ReceiptDao
import com.recippie.doctor.app.data.ReceiptData
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(ReceiptData::class, AlarmData::class), version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun receiptDao() : ReceiptDao
    abstract fun alarmDao() : AlarmDao


    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AppDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    ///.addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        /*private class WordDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            *//**
             * Override the onCreate method to populate the database.
             *//*
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.receiptDao())
                    }
                }
            }
        }*/

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */

        /*suspend fun populateDatabase(wordDao: WordDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            wordDao.deleteAll()

            var word = Word("Hello")
            wordDao.insert(word)
            word = Word("World!")
            wordDao.insert(word)
        }*/
    }
}