package com.recippie.doctor.app.util

import androidx.annotation.StringRes
import com.recippie.doctor.app.R

enum class PushNotificationChannel(
    @StringRes val channelId: Int,
    @StringRes val channelNameId: Int,
    @StringRes val channelDescId: Int,
    val hasSound: Boolean,
) {
    ALIVE(R.string.alive_channel_id, R.string.alive_channel_name, R.string.notification_description, true)
}