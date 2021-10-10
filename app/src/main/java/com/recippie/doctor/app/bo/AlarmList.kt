package com.recippie.doctor.app.bo

import android.os.Build
import com.recippie.doctor.app.pojo.Alarm
import java.text.SimpleDateFormat
import java.time.LocalDateTime

class AlarmList(description: String, eachTime: String, duringTime: String, timeInit: String, dayInit: String) {

    fun createAlarms(description: String, eachTime: String, duringTime: String, timeInit: String, dayInit: String): List<Alarm> {
        //https://stackoverflow.com/questions/53781154/kotlin-android-java-string-datetime-format-api21
        val time = dayInit+"T"+timeInit+":00"
        var intakeDateTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.parse(time)
        } else {
            TODO("VERSION.SDK_INT < O")
            //val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            //parser.parse(time)
        }

        val alarmLst: MutableList<Alarm> = mutableListOf()
        val intakeTimes = 24/eachTime.toInt()*duringTime.toInt()
        for (i in 1..intakeTimes) {
            alarmLst.add(Alarm(intakeDateTime as LocalDateTime, description));
            intakeDateTime = intakeDateTime.plusHours(eachTime.toLong())
        }
        return alarmLst;
    }
}