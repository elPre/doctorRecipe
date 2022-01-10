package com.recippie.doctor.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.recippie.doctor.app.databinding.MedicineIntakeScheduleItemBinding
import com.recippie.doctor.app.event.CurrentHistoryActionEvent
import com.recippie.doctor.app.holder.BaseBindingViewHolder
import com.recippie.doctor.app.holder.CurrentAndHistoryInfoViewHolder
import com.recippie.doctor.app.pojo.HistoryInfo
import com.recippie.doctor.app.pojo.ViewScheduleReceipt

class HistoryBaseAdapter(
    private val onAction: (CurrentHistoryActionEvent) -> Unit
): BaseBindingAdapter<ViewScheduleReceipt, MedicineIntakeScheduleItemBinding>() {

    var moduleData: HistoryInfo?= null
        set(value) {
            field = value
            val myScheduleList = value?.data ?: emptyList()
            if(myScheduleList.isNotEmpty()) {
                setData(myScheduleList.toMutableList())
            }
        }

    override fun getViewHolder(inflater: LayoutInflater, container: ViewGroup?, viewType: Int):
            BaseBindingViewHolder<ViewScheduleReceipt, MedicineIntakeScheduleItemBinding> {
        val binding = MedicineIntakeScheduleItemBinding.inflate(inflater, container, false)
        return CurrentAndHistoryInfoViewHolder(binding, onAction, ::getItem)
    }
}