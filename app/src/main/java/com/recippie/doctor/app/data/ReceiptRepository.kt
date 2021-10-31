package com.recippie.doctor.app.data

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.room.Room
import com.recippie.doctor.app.db.AppDataBase

class ReceiptRepository(app: Application) {
    private val db = Room.databaseBuilder(
        app,
        AppDataBase::class.java, "database-name"
    ).build()


    fun getLastReceipt() = db.receiptDao().getLastReceipt()

    fun existReceipt() : Boolean = db.receiptDao().existReceipt()

    fun getCurrentReceipt(lastReceipt: Int): List<ReceiptData> {
        return db.receiptDao().currentReceipt(lastReceipt)
    }
    
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertReceipt(receiptData: ReceiptData) {
        db.receiptDao().insertReceipt(receiptData)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun removeAlarm(receiptData: ReceiptData) {
        db.receiptDao().delete(receiptData)
    }
}