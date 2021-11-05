package com.recippie.doctor.app.bo

import com.recippie.doctor.app.data.ReceiptData
import com.recippie.doctor.app.pojo.Receipt
import com.recippie.doctor.app.repository.IReceiptRepository
import java.util.Date

class BuildReceiptBO(private val repo: IReceiptRepository) : IBuildReceiptBO {

    override suspend fun calculateTimes(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun calculateDates(): List<Date> {
        TODO("Not yet implemented")
    }

    override suspend fun buildAlarmsForReceipt(dates: List<Date>, times: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentReceipt(): List<Receipt> {
        if (repo.existReceipt()) {
            val lastReceipt = repo.getLastReceipt()
            lastReceipt?.run {
                return repo.getCurrentReceipt(this).map {
                    Receipt(it.description, it.eachTime, it.duringTime)
                }
            }
        }
        return emptyList()
    }

    override suspend fun saveReceipt(list: MutableList<Receipt>) {
        if (list.isEmpty()) return
        var stopInsert = false
        list.forEach {
            stopInsert = when {
                it.description.isNullOrEmpty() || it.duringTime.isNullOrEmpty() || it.eachTime.isNullOrEmpty() -> true
                else -> false
            }
            if (stopInsert) return@forEach
        }
        if (stopInsert) return//show snackbar saying that no data was saved
        var receiptNumber = repo.getLastReceipt()
        if (receiptNumber == null) {
            receiptNumber = 1
        } else {
            receiptNumber++
        }
        repo.insertReceipt(list.map {
            ReceiptData(
                numReceipt = receiptNumber,
                description = it.description,
                duringTime = it.duringTime,
                eachTime = it.eachTime)
        })
    }
}