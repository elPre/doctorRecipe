package com.recippie.doctor.app

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

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
}