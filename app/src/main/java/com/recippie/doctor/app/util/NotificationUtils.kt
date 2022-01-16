package com.recippie.doctor.app.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.recippie.doctor.app.R

object NotificationUtils {

    fun buildNotificationManager(
        context: Context,
        channel: PushNotificationChannel
    ): NotificationManager? {
        // Since android Oreo notification channel is needed.
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.getSystemService(NotificationManager::class.java)?.apply {
                val name = context.getString(channel.channelNameId)
                val shouldCreate = getNotificationChannel(name) == null

                if (shouldCreate) {
                    createNotificationChannel(
                        NotificationChannel(
                            context.getString(channel.channelId),
                            name,
                            NotificationManager.IMPORTANCE_HIGH
                        ).apply {
                            description = context.getString(channel.channelDescId)
                            setShowBadge(true)
                            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                            if (!channel.hasSound)
                                setSound(null, null)
                        })
                }
            }
        } else {
            context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
        }
    }

    fun sendNotification(context: Context, title: String, messageBody: String) {

        val builder = NotificationCompat.Builder(context, context.getString(PushNotificationChannel.ALIVE.channelId))
            .setSmallIcon(R.drawable.ic_profile_empty)
            .setColor(context.getColor(R.color.light_blue))
            .setContentTitle("Medicine Intake")
            .setContentText(title)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(messageBody)
            )
            .setAutoCancel(true)
            .setGroup("SHOW_MEDICINES_NOTIFICATION")
            .setPriority(NotificationCompat.PRIORITY_MAX) //Important for heads-up notification
            .setDefaults(NotificationCompat.DEFAULT_SOUND or NotificationCompat.DEFAULT_VIBRATE) //Important for heads-up notification


        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(PushNotificationChannel.ALIVE.channelId, builder.build())
        }

    }


    fun clearNotifications(ctx: Context) {
        NotificationManagerCompat.from(ctx).cancelAll()
    }

    fun notificationsEnabled(ctx: Context): Boolean {
        val mgr = NotificationManagerCompat.from(ctx)
        if (!mgr.areNotificationsEnabled()) {
            return false
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PushNotificationChannel.values().all { c ->
                mgr.getNotificationChannel(ctx.getString(c.channelId))
                    ?.importance != NotificationManager.IMPORTANCE_NONE
            } // All currently-created channels need to be enabled
        } else {
            true
        }
    }
}