package com.recippie.doctor.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.recippie.doctor.app.databinding.MedicineIntakeScheduleItemBinding
import com.recippie.doctor.app.event.ReceiptActionEvent
import com.recippie.doctor.app.holder.BaseBindingViewHolder
import com.recippie.doctor.app.pojo.ViewScheduleProgram
import com.recippie.doctor.app.pojo.ViewScheduleReceipt

class CalculationProgramAdapter(
    val onAction: (ReceiptActionEvent) -> Unit
): BaseBindingAdapter<ViewScheduleReceipt, MedicineIntakeScheduleItemBinding>() {

    var moduleData: ViewScheduleProgram?= null
        set(value) {
            field = value
            val myScheduleList = value?.data ?: emptyList()
            if(myScheduleList.isNotEmpty()) setData(myScheduleList)
        }

    override fun getViewHolder(inflater: LayoutInflater, container: ViewGroup?, viewType: Int): BaseBindingViewHolder<ViewScheduleReceipt, MedicineIntakeScheduleItemBinding> {

    }
}