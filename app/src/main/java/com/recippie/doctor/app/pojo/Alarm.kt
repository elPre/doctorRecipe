package com.recippie.doctor.app.pojo

import java.time.LocalDateTime

data class Alarm(
    val dateTime: LocalDateTime,
    val message: String
)
