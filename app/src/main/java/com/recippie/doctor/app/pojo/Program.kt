package com.recippie.doctor.app.pojo

data class Program(val medicine: String,
                   val date: String,
                   val time: String,
                   val numReceipt: Long,
                   val numAlarm: Int? = null,
                   val during: String? = null)