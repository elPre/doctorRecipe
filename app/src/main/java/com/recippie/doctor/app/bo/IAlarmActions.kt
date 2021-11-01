package com.recippie.doctor.app.bo

interface IAlarmActions: SetAlarm, CancelAlarm, BuildAlarm

interface SetAlarm {
    fun setAlarm()
}

interface CancelAlarm {
    fun cancelAlarm()
}

interface BuildAlarm {
    fun buildAlarm()
}