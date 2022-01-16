package com.recippie.doctor.app.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.recippie.doctor.app.bo.WorkerRestoreAlarms

class RestoreAlarmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.run {
            if (this.action == Intent.ACTION_BOOT_COMPLETED) {
                context?.let {
                    WorkerRestoreAlarms.restoreAlarms(it)
                }
            }
        }
    }
}