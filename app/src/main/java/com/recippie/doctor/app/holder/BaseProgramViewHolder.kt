package com.recippie.doctor.app.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.recippie.doctor.app.pojo.ReceiptModuleItem

abstract class BaseProgramViewHolder <T : ReceiptModuleItem>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: T)
}