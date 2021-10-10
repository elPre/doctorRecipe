package com.recippie.doctor.app.event

sealed class ReceiptActionEvent {
    object OpenCalendar : ReceiptActionEvent()
    object OpenClock : ReceiptActionEvent()
}
