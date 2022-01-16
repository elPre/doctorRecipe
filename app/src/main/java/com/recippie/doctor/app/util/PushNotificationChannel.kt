package com.recippie.doctor.app.util

import androidx.annotation.StringRes
import com.recippie.doctor.app.R

enum class PushNotificationChannel(
    @StringRes val channelId: Int,
    @StringRes val channelNameId: Int,
    @StringRes val channelDescId: Int,
    val hasSound: Boolean,
) {
    ALIVE(R.string.alive_channel_id,R.string.alive_channel_name,R.string.notification_description,true)

//    GROUPS(R.string.default_notification_channel_id, R.string.channel_name_groups, R.string.channel_description_groups, true),
//    UPLOAD(R.string.default_files_upload_channel_id, R.string.channel_name_files_upload, R.string.channel_description_files_upload, false),
//    LOG_WORKOUT(R.string.default_log_workout_channel_id, R.string.channel_name_log_workout, R.string.channel_description_log_workout, false),
//    BODI(R.string.default_bodi_channel_id, R.string.channel_name_bodi, R.string.channel_description_bodi, true),
//    MARKETING(R.string.default_marketing_channel_id, R.string.channel_name_marketing, R.string.channel_description_marketing, true),
}