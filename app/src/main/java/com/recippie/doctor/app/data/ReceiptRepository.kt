package com.recippie.doctor.app.data

import androidx.annotation.WorkerThread

class ReceiptRepository(private val receiptDao: ReceiptDao) {
    fun getLastReceipt() = receiptDao.getLastReceipt()

    fun getCurrentReceipt(lastReceipt: Int): List<ReceiptData> {
        return receiptDao.currentReceipt(lastReceipt)
    }
    
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAlarm(receiptData: ReceiptData) {
        receiptDao.insertReceipt(receiptData)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun removeAlarm(receiptData: ReceiptData) {
        receiptDao.delete(receiptData)
    }
}