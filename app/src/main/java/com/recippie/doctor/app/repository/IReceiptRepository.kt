package com.recippie.doctor.app.repository

import com.recippie.doctor.app.data.ReceiptData

interface IReceiptRepository : GetLastReceipt,
    CheckIfReceiptExist,
    InsertReceipt,
    DeleteReceipt,
    GetCurrentReceipt,
    UpdateReceipt

interface GetLastReceipt {
    suspend fun getLastReceipt(): Long?
}

interface CheckIfReceiptExist {
    suspend fun existReceipt(): Boolean
}

interface InsertReceipt {
    suspend fun insertReceipt(receiptData: List<ReceiptData>)
}

interface DeleteReceipt {
    suspend fun deleteReceipt(receiptData: ReceiptData)
}

interface GetCurrentReceipt {
    suspend fun getCurrentReceipt(lastReceipt: Long): List<ReceiptData>
}

interface UpdateReceipt {
    suspend fun updateReceipt(receiptData: List<ReceiptData>)
}