package com.recippie.doctor.app

import com.recippie.doctor.app.pojo.Program
import com.recippie.doctor.app.pojo.Receipt
import org.junit.Test

import org.junit.Assert.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

        val list = listOf(
            //Receipt(description = "medicine 1", eachTime = "8", duringTime = "3"),
            Receipt(description = "medicine 2", eachTime = "12", duringTime = "2"),
            //Receipt(description = "medicine 3", eachTime = "6", duringTime = "2")
        )

        val FORMAT_DATE = "dd MMM yyyy"
        val FORMAT_TIME = "hh:mm a"
        val dateFormatter = DateTimeFormatter.ofPattern(FORMAT_DATE)
        val timeFormatter = DateTimeFormatter.ofPattern(FORMAT_TIME)

        val time: LocalDateTime = LocalDateTime.now()
        val resultList = mutableListOf<Program>()

        list.forEach { receipt ->
            var localTime = time
            resultList.add(
                Program(
                    medicine = receipt.description,
                    date = dateFormatter.format(time),
                    time = timeFormatter.format(time)
                )
            )
            val intakeTimes = 24 * receipt.duringTime.toInt() / receipt.eachTime.toInt()
            for (i in 1 until intakeTimes) {
                localTime = localTime.plusHours(receipt.eachTime.toLong())
                resultList.add(
                    Program(
                        medicine = receipt.description,
                        date = dateFormatter.format(localTime),
                        time = timeFormatter.format(localTime)
                    )
                )
            }
        }
        resultList.sortedBy { it.date }.toList()

        assertEquals(23, resultList.size)
    }
}