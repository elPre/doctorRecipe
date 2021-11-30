package com.recippie.doctor.app

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.recippie.doctor.app.bo.BuildReceiptBO
import com.recippie.doctor.app.bo.IBuildReceiptBO
import com.recippie.doctor.app.pojo.Program
import com.recippie.doctor.app.pojo.Receipt

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAmount
import java.time.temporal.TemporalUnit
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
//    @Test
//    fun useAppContext() {
//        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.recippie.doctor.app", appContext.packageName)
//    }

    @Test
    fun testDateCreation() {
        var time: LocalDateTime = LocalDateTime.now()

        val dTF: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM uuuu")
        val checkingDate = dTF.format(time)

        val t1= time.plusHours(8)

        val dTF2: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
        val testJustHour = dTF2.format(t1)

        val t2= t1.plusHours(8)
        val t3= t2.plusHours(8)
        val t4= t3.plusHours(8)
        val t5= t4.plusHours(8)
        assertEquals(1,1)
    }

    @Test
    fun testCreateSchedule() {

        val bo = object : IBuildReceiptBO {
            override suspend fun calculateDateAndTime(
                dateTime: LocalDateTime,
                list: List<Receipt>
            ): List<Program> {

                if (list.isEmpty()) return emptyList()
                val resultList = mutableListOf<Program>()
                val FORMAT_DATE = "dd MMM yyyy"
                val FORMAT_TIME = "hh:mm a"
                val dateFormatter = DateTimeFormatter.ofPattern(FORMAT_DATE)
                val timeFormatter = DateTimeFormatter.ofPattern(FORMAT_TIME)

                val time: LocalDateTime = dateTime

                list.forEach { receipt ->
                    var localTime = time
                    resultList.add(Program(
                        medicine = receipt.description,
                        date = dateFormatter.format(time),
                        time = timeFormatter.format(time)
                    ))
                    val intakeTimes = 24 * receipt.duringTime.toInt() / receipt.eachTime.toInt()
                    for (i in 2 until intakeTimes) {
                        localTime = localTime.plusHours(receipt.eachTime.toLong())
                        resultList.add(Program(
                            medicine = receipt.description,
                            date = dateFormatter.format(localTime),
                            time = timeFormatter.format(localTime)
                        ))
                    }
                }
                return resultList.sortedBy { it.date }.toList()
            }

            override suspend fun buildAlarmsForReceipt(dates: List<Date>, times: Int) { }

            override suspend fun getCurrentReceipt(): List<Receipt> { return emptyList() }

            override suspend fun saveReceipt(list: List<Receipt>) { }

        }

        val listReceipt = listOf(Receipt(description = "medicine", eachTime = "8", duringTime = "3"))

        val programList = bo.calculateDateAndTime(LocalDateTime.now(), listReceipt)
        Log.d("this is the data","program list is --> $programList")

    }
}