package com.recippie.doctor.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.recippie.doctor.app.databinding.ReceiptItemBinding
import com.recippie.doctor.app.event.ReceiptActionEvent
import com.recippie.doctor.app.holder.BaseBindingViewHolder
import com.recippie.doctor.app.holder.ReceiptHolder
import com.recippie.doctor.app.pojo.Receipt
import com.recippie.doctor.app.util.inflater

class ReceiptAdapter(
    private val onAction: (ReceiptActionEvent) -> Unit,
) : BaseBindingAdapter<Receipt, ReceiptItemBinding>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiptHolder {
        return ReceiptHolder(
            ReceiptItemBinding.inflate(parent.inflater, parent, false), onAction, ::getItem
        )
    }

    override fun getViewHolder(inflater: LayoutInflater, container: ViewGroup?, viewType: Int):
            BaseBindingViewHolder<Receipt, ReceiptItemBinding> {
        val binding = ReceiptItemBinding.inflate(inflater, container, false)
        return ReceiptHolder(binding, onAction, ::getItem)
    }
}