package com.recippie.doctor.app.bo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.recippie.doctor.app.R
import com.recippie.doctor.app.activity.MainActivity
import com.recippie.doctor.app.util.NotificationUtils
import com.recippie.doctor.app.util.PushNotificationChannel

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.run {
            if (intent.action == AlarmBO.SHOW_NOTIFICATION_ACTION) {
                context?.run {
                    NotificationUtils.buildNotificationManager(context.applicationContext, PushNotificationChannel.ALIVE)
                    NotificationUtils.sendNotification(
                        context,
                        getString(R.string.title_reminder),
                        intent.extras?.getString(AlarmBO.BODY_MSG) ?: "",
                        MainActivity::class.java)
                }
            }
        }
    }
}
