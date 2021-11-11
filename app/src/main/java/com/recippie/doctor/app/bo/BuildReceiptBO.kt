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
                val currentTime = System.currentTimeMillis()
                val receiptList = repo.getCurrentReceipt(this)
                receiptList.filter {
                    currentTime < (it.numReceipt + (MILLISECONDS_IN_DAY.times(it.duringTime.toLong())))
                }.size.let {
                    return if (it > 0) {
                        receiptList.map { data ->
                            Receipt(
                                numReceipt = data.numReceipt,
                                description = data.description,
                                eachTime = data.eachTime,
                                duringTime = data.duringTime
                            )
                        }
                    } else {
                        emptyList()
                    }
                }
            }
        }
        return emptyList()
    }

    override suspend fun saveReceipt(list: List<Receipt>) {
        if (list.isEmpty()) return
        val dateTimeInMilliseconds = System.currentTimeMillis()
        val dbList = list.map {
            ReceiptData(
                numReceipt = it.numReceipt ?: dateTimeInMilliseconds,
                description = it.description,
                duringTime = it.duringTime,
                eachTime = it.eachTime)
        }
        if(list[0].numReceipt != null) {
            repo.updateReceipt(dbList)
        } else {
            repo.insertReceipt(dbList)
        }
    }

    companion object {
        private const val MILLISECONDS_IN_DAY = 86400000
    }
}