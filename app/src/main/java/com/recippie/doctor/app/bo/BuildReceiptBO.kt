package com.recippie.doctor.app.bo

import android.annotation.SuppressLint
import com.recippie.doctor.app.data.AlarmData
import com.recippie.doctor.app.data.ReceiptData
import com.recippie.doctor.app.pojo.Program
import com.recippie.doctor.app.pojo.Receipt
import com.recippie.doctor.app.repository.IAlarmRepository
import com.recippie.doctor.app.repository.IReceiptRepository
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class BuildReceiptBO(private val receiptRepo: IReceiptRepository,
    private val alarmRepo: IAlarmRepository? = null) : IBuildReceiptBO {

    override suspend fun calculateDateAndTime(dateTime: LocalDateTime, list: List<Receipt>): List<Program> {

        if (list.isEmpty()) return emptyList()
        val resultList = mutableListOf<Program>()

        val dateFormatter = DateTimeFormatter.ofPattern(FORMAT_DATE)
        val timeFormatter = DateTimeFormatter.ofPattern(FORMAT_TIME)

        val time: LocalDateTime = dateTime

        list.reversed().forEach { receipt ->
            var localTime = time
            resultList.add(
                Program(
                    medicine = receipt.description,
                    date = dateFormatter.format(time),
                    time = timeFormatter.format(time),
                    numReceipt = receipt.numReceipt ?: 0
                )
            )
            val intakeTimes = HRS_IN_DAY_24 * receipt.duringTime.toInt() / receipt.eachTime.toInt()
            for (i in 1 until intakeTimes) {
                localTime = localTime.plusHours(receipt.eachTime.toLong())
                resultList.add(
                    Program(
                        medicine = receipt.description,
                        date = dateFormatter.format(localTime),
                        time = timeFormatter.format(localTime),
                        numReceipt = receipt.numReceipt ?: 0
                    )
                )
            }
        }
        return resultList.toList()
    }

    override suspend fun getCurrentReceipt(): List<Receipt> {
        if (receiptRepo.existReceipt()) {
            val lastReceipt = receiptRepo.getLastReceipt()
            lastReceipt?.run {
                val currentTime = System.currentTimeMillis()
                val receiptList = receiptRepo.getCurrentReceipt(this)
                receiptList.filter {
                    currentTime < (it.numReceipt + (MILLISECONDS_IN_DAY.times(it.duringTime.toLong())))
                }.size.let {
                    return if (it > 0) {
                        receiptList.map { data ->
                            Receipt(
                                numReceipt = data.numReceipt,
                                description = data.description,
                                eachTime = data.eachTime,
                                duringTime = data.duringTime,
                                numMedicine = data.numMedicine
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
                eachTime = it.eachTime,
                numMedicine = it.numMedicine ?: 0
            )
        }
        when {
            list[0].numReceipt != null -> receiptRepo.updateReceipt(dbList)
            else -> receiptRepo.insertReceipt(dbList)
        }
    }

    override suspend fun deleteReceipt(receiptDelete: Receipt) {
        when {
            receiptDelete.numMedicine == null || receiptDelete.numMedicine == 0 -> return
            receiptDelete.numReceipt == null || receiptDelete.numReceipt == 0L -> return
            else -> {
                receiptRepo.deleteReceipt(
                    ReceiptData(
                    numMedicine = receiptDelete.numMedicine,
                    numReceipt = receiptDelete.numReceipt,
                    description = receiptDelete.description,
                    eachTime = receiptDelete.eachTime,
                    duringTime = receiptDelete.duringTime
                    )
                )
                alarmRepo?.getAlarms(receiptDelete.numReceipt)?.let { alarmList ->
                    alarmList.forEach {
                        alarmRepo.removeAlarm(it)
                    }
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    override suspend fun saveProgram(list: List<Program>) {
        val simpleDateFormat = SimpleDateFormat("$FORMAT_DATE $FORMAT_TIME")
        val alarmDataList = if (list.isNotEmpty()) alarmRepo?.getAlarms(list[0].numReceipt) else emptyList()
        when {
            alarmDataList.isNullOrEmpty().not() -> { //Update
                if (list.size == alarmDataList?.size) {
                    val updateList = mutableListOf<AlarmData>()
                    for (i in list.indices) {
                        val updateData = list[i]
                        val idRow = alarmDataList[i]
                        val dateTime = updateData.date+" "+updateData.time
                        updateList.add(
                            AlarmData(
                                numAlarm = idRow.numAlarm,
                                numReceipt = updateData.numReceipt,
                                alarm = simpleDateFormat.parse(dateTime) ?: Date(),
                                message = updateData.medicine
                            )
                        )
                    }
                    alarmRepo?.updateAlarms(updateList)
                }
            }
            else -> { //Save
                alarmRepo?.insertAlarm(
                    list.map {
                        val dateTime = it.date+" "+it.time
                        AlarmData(
                            numReceipt = it.numReceipt,
                            alarm = simpleDateFormat.parse(dateTime) ?: Date(),
                            message = it.medicine
                        )
                    }
                )
            }
        }
    }

    companion object {
        private const val MILLISECONDS_IN_DAY = 86400000
        private const val FORMAT_DATE = "dd MMM yyyy"
        private const val FORMAT_TIME = "hh:mm a"
        private const val HRS_IN_DAY_24 = 24
    }
}