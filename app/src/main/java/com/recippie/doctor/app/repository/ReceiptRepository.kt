package com.recippie.doctor.app.repository

import android.app.Application
import com.recippie.doctor.app.data.ReceiptData
import com.recippie.doctor.app.db.AppDataBase

class ReceiptRepository(val app: Application): IReceiptRepository {

    private val receiptDao = AppDataBase.getInstance(app).receiptDao()

    override suspend fun getLastReceipt() = receiptDao.getLastReceipt()

    override suspend fun existReceipt(): Boolean = receiptDao.existReceipt()

    override suspend fun insertReceipt(receiptData: List<ReceiptData>) = receiptDao.insertReceipt(*receiptData.toTypedArray())

    override suspend fun deleteReceipt(receiptData: ReceiptData) = receiptDao.deleteReceipt(receiptData)

    override suspend fun getCurrentReceipt(lastReceipt: Int) = receiptDao.currentReceipt(lastReceipt)
}