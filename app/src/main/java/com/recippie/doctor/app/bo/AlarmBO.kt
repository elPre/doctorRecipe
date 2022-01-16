package com.recippie.doctor.app.bo

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.recippie.doctor.app.data.AlarmData
import com.recippie.doctor.app.repository.AlarmRepository
import com.recippie.doctor.app.repository.ReceiptRepository

class AlarmBO(val context: Context) : IAlarmActions {

    override suspend fun buildAlarm() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val receiptRepo = BuildReceiptBO(ReceiptRepository(context as Application), AlarmRepository(context))
        val currentAlarmList = receiptRepo.getCurrentAlarmList()

        if(currentAlarmList.isNotEmpty()) {
            currentAlarmList.forEach { alarmData ->
                val intent = createIntent(alarmData)
                val pendingIntent = PendingIntent.getBroadcast(context, alarmData.alarm.time.toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT)
                alarmManager?.setExact(AlarmManager.RTC_WAKEUP, alarmData.alarm.time, pendingIntent)
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