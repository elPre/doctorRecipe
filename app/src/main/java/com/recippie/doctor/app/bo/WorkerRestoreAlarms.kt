package com.recippie.doctor.app.bo

import android.content.Context
import androidx.work.*

class WorkerRestoreAlarms (ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        return try {
            val alarmBO : IAlarmActions = AlarmBO(applicationContext)
            alarmBO.buildAlarm()
            Result.success()
        } catch (throwable: Throwable) {
            Result.failure()
        }
    }

    companion object {

        private const val TAG = "WorkerRestoreAlarms"

        fun restoreAlarms(context: Context) {
            val workRequest = OneTimeWorkRequestBuilder<WorkerRestoreAlarms>()
                .build()
            WorkManager.getInstance(context)
                .beginUniqueWork(TAG, ExistingWorkPolicy.REPLACE, workRequest).enqueue()
        }
    }
}