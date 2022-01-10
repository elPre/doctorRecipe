package com.recippie.doctor.app.bo

import android.annotation.SuppressLint
import com.recippie.doctor.app.data.AlarmData
import com.recippie.doctor.app.data.ReceiptData
import com.recippie.doctor.app.pojo.Program
import com.recippie.doctor.app.pojo.Receipt
import com.recippie.doctor.app.repository.IAlarmRepository
import com.recippie.doctor.app.repository.IReceiptRepository
import java.text.SimpleDateFormat
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
                    numReceipt = receipt.numReceipt ?: 0,
                    during = receipt.duringTime
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
                        numReceipt = receipt.numReceipt ?: 0,
                        during = receipt.duringTime
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
        val newElementsList = mutableListOf<Receipt>()
        list.forEach { receipt ->
            if (receipt.numReceipt == null) {
                newElementsList.add(receipt)
            }
        }
        val areAllNewItems = list.filter { it.numReceipt == null }
        when {
            areAllNewItems.size == list.size -> {
                receiptRepo.insertReceipt(list.map {
                    ReceiptData(
                        numReceipt = it.numReceipt ?: dateTimeInMilliseconds,
                        description = it.description,
                        duringTime = it.duringTime,
                        eachTime = it.eachTime,
                        numMedicine = it.numMedicine ?: 0
                    )
                })
            }
            newElementsList.size > 0 -> {
                val alreadyExistsNumReceipt = list.find { it.numReceipt != null }
                if (alreadyExistsNumReceipt?.numReceipt != null) {
                    receiptRepo.insertReceipt(
                        newElementsList.map {
                            ReceiptData(
                                numReceipt = alreadyExistsNumReceipt.numReceipt,
                                description = it.description,
                                duringTime = it.duringTime,
                                eachTime = it.eachTime,
                                numMedicine = 0
                            )
                        }
                    )
                }
                receiptRepo.updateReceipt(list.filter { it.numReceipt != null }.map {
                    ReceiptData(
                        numReceipt = it.numReceipt ?: dateTimeInMilliseconds,
                        description = it.description,
                        duringTime = it.duringTime,
                        eachTime = it.eachTime,
                        numMedicine = it.numMedicine ?: 0
                    )
                })
            }
            else -> {
                receiptRepo.updateReceipt(list.map {
                    ReceiptData(
                        numReceipt = it.numReceipt ?: dateTimeInMilliseconds,
                        description = it.description,
                        duringTime = it.duringTime,
                        eachTime = it.eachTime,
                        numMedicine = it.numMedicine ?: 0
                    )
                })
            }
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
                alarmRepo?.getSpecificAlarm(
                    AlarmData(
                        numReceipt = receiptDelete.numReceipt,
                        message = receiptDelete.description,
                        during = "",
                        timeText = "",
                        dateText = "",
                        alarm = Date()
                    )
                )?.let { alarmToDelete ->
                    alarmRepo.deleteSpecificAlarm(alarm = alarmToDelete[0])
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    override suspend fun saveProgram(list: List<Program>) {

        val simpleDateFormat = SimpleDateFormat("$FORMAT_DATE $FORMAT_TIME")
        val alarmDataList = if (list.isNotEmpty()) alarmRepo?.getAlarms(list[0].numReceipt) else emptyList()

        when {

            alarmDataList != null && (list.size > alarmDataList.size || list.size < alarmDataList.size) -> {//there is new receipts in the

                alarmDataList.let { alarmList ->
                    alarmList.forEach {
                        alarmRepo?.removeAlarm(it)
                    }
                }

                alarmRepo?.insertAlarm(
                    list.map {
                        val dateTime = it.date+" "+it.time
                        AlarmData(
                            numReceipt = it.numReceipt,
                            alarm = simpleDateFormat.parse(dateTime) ?: Date(),
                            message = it.medicine,
                            dateText = it.date,
                            timeText = it.time,
                            during = it.during ?: ""
                        )
                    }
                )
            }

            list.size == alarmDataList?.size -> { //Update
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
                            message = updateData.medicine,
                            dateText = updateData.date,
                            timeText = updateData.time,
                            during = idRow.during
                        )
                    )
                }
                alarmRepo?.updateAlarms(updateList)
            }

            else -> { //Save
                alarmRepo?.insertAlarm(
                    list.map {
                        val dateTime = it.date+" "+it.time
                        AlarmData(
                            numReceipt = it.numReceipt,
                            alarm = simpleDateFormat.parse(dateTime) ?: Date(),
                            message = it.medicine,
                            dateText = it.date,
                            timeText = it.time,
                            during = it.during ?: ""
                        )
                    }
                )
            }
        }
    }

    override suspend fun getCurrentAlarmList(): List<AlarmData> {
        return receiptRepo.existReceipt().let {
            receiptRepo.getLastReceipt().run {
                val currentTime = System.currentTimeMillis()
                alarmRepo?.getAlarms(this ?: 0)?.filter {
                    currentTime < (it.numReceipt + (MILLISECONDS_IN_DAY.times(it.during.toLong())))
                }
            } ?: emptyList()
        }
    }

    override suspend fun getHistoryAlarms(): List<AlarmData> {
        return receiptRepo.existReceipt().let {
            val currentTime = System.currentTimeMillis()
            alarmRepo?.getAllAlarms()?.filter {
                currentTime > (it.numReceipt + (MILLISECONDS_IN_DAY.times(it.during.toLong())))
            }
        } ?: emptyList()

    }

    companion object {
        private const val MILLISECONDS_IN_DAY = 86400000
        private const val FORMAT_DATE = "dd MMM yyyy"
        private const val FORMAT_TIME = "hh:mm a"
        private const val HRS_IN_DAY_24 = 24
    }
}