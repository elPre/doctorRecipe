package com.recippie.doctor.app.event

sealed class CurrentHistoryActionEvent {
    object WatchZoom : CurrentHistoryActionEvent()
    object Remove : CurrentHistoryActionEvent()
}