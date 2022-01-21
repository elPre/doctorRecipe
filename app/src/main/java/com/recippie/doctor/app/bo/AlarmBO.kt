package com.recippie.doctor.app.bo

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.recippie.doctor.app.data.AlarmData
import com.recippie.doctor.app.repository.AlarmRepository
import com.recippie.doctor.app.repository.ReceiptRepository
import java.util.*

class AlarmBO(val context: Context) : IAlarmActions {

    override suspend fun buildAlarm() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val receiptRepo = BuildReceiptBO(ReceiptRepository(context as Application), AlarmRepository(context))
        val currentAlarmList = receiptRepo.getCurrentAlarmList().groupBy { it.alarm }
        if(currentAlarmList.isNotEmpty()) {
            currentAlarmList.forEach { alarmDataList ->
                var msg = ""
                var noReceipt = 1
                var date = Date()
                alarmDataList.value.forEach {
                    date = it.alarm
                    msg += "$noReceipt.- ${it.message} \n"
                    noReceipt++
                }
                val alarm = AlarmData(0, 1234, date, msg, "", "", "")
                val intent = createIntent(alarm)
                val pendingIntent = PendingIntent.getBroadcast(context, alarm.alarm.time.toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT)
                alarmManager?.setExact(AlarmManager.RTC_WAKEUP, alarm.alarm.time, pendingIntent)
            }
        }
    }

    override suspend fun deleteAlarms(listToDelete: List<AlarmData>) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        listToDelete.forEach{
                alarmData ->
            val intent = createIntent(alarmData)
            val pendingIntent = PendingIntent.getBroadcast(context, alarmData.alarm.time.toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager?.cancel(pendingIntent)
        }
    }

    private fun createIntent(alarmData: AlarmData): Intent {
        return Intent(context, NotificationReceiver::class.java).apply {
            action = SHOW_NOTIFICATION_ACTION
            putExtra(BODY_MSG, alarmData.message)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
    }

    companion object {
        const val BODY_MSG = "intent-body-message"
        const val SHOW_NOTIFICATION_ACTION = "com.scheduler.receipts.SHOW_NOTIFICATION"
    }

}