package com.recippie.doctor.app.interfaces

import com.recippie.doctor.app.adapter.ReceiptAdapter
import com.recippie.doctor.app.pojo.Receipt
import kotlinx.coroutines.Job

interface BaseReceipt : LoadReceiptPage, LoadReceipt, AddReceipt, DeleteReceipt, SaveFormReceipt

interface LoadReceiptPage {
    fun loadReceiptPage(forceReload: Boolean = true): Job
}

interface LoadReceipt {
    fun loadReceipt(): Job
}

interface AddReceipt {
    fun addReceipt(adapter: ReceiptAdapter): Job
}

interface DeleteReceipt {
    fun deleteReceipt(receiptDelete: Receipt): Job
}

interface SaveFormReceipt {
    fun saveFormReceipt(list: List<Receipt>): Job
}