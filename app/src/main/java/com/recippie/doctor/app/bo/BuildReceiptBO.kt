package com.recippie.doctor.app.bo

import com.recippie.doctor.app.pojo.Receipt
import com.recippie.doctor.app.repository.IReceiptRepository
import java.util.Date

class BuildReceiptBO(private val repo: IReceiptRepository): IBuildReceiptBO {

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
            return repo.getCurrentReceipt(lastReceipt).map {
                Receipt(it.description, it.eachTime, it.duringTime)
            }
        }
        return emptyList()
    }
}