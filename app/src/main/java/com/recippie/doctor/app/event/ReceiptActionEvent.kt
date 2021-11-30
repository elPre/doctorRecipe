package com.recippie.doctor.app.event

sealed class ReceiptActionEvent {
    object OpenCalendar : ReceiptActionEvent()
    object OpenClock : ReceiptActionEvent()
    object SaveProgram : ReceiptActionEvent()
    object ProgramSchedule : ReceiptActionEvent()
    object NotEmptyFieldsAllowed : ReceiptActionEvent()
}
