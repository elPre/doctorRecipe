package com.recippie.doctor.app.data

import android.app.Application
import com.recippie.doctor.app.db.AppDataBase

class ReceiptRepository(val app: Application) {

    private val receiptDao = AppDataBase.getInstance(app).receiptDao()

    suspend fun getLastReceipt() = receiptDao.getLastReceipt()

    suspend fun existReceipt() : Boolean = receiptDao.existReceipt()

    suspend fun getCurrentReceipt(lastReceipt: Int): List<ReceiptData> {
        return receiptDao.currentReceipt(lastReceipt)
    }

    suspend fun insertReceipt(receiptData: ReceiptData) {
        receiptDao.insertReceipt(receiptData)
    }

    suspend fun removeAlarm(receiptData: ReceiptData) {
        receiptDao.delete(receiptData)
    }
}