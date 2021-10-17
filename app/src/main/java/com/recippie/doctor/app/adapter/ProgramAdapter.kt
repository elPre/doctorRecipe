package com.recippie.doctor.app.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.recippie.doctor.app.databinding.MedicineIntakeProgramItemBinding
import com.recippie.doctor.app.databinding.MedicineIntakeViewScheduleSaveBinding
import com.recippie.doctor.app.event.ReceiptActionEvent
import com.recippie.doctor.app.holder.ReceiptBaseViewHolder
import com.recippie.doctor.app.holder.CreateProgramViewHolder
import com.recippie.doctor.app.holder.ViewScheduleReceiptViewHolder
import com.recippie.doctor.app.moduleitems.ModuleItemDataWrapper
import com.recippie.doctor.app.pojo.ReceiptItemType
import com.recippie.doctor.app.pojo.ReceiptModuleItem
import com.recippie.doctor.app.util.inflater

typealias ProgramBindingBaseViewHolder = ReceiptBaseViewHolder<ModuleItemDataWrapper<ReceiptModuleItem>, ViewBinding>

class ProgramAdapter(
    private val onAction: (ReceiptActionEvent) -> Unit
): ListAdapter<ModuleItemDataWrapper<ReceiptModuleItem>, ProgramBindingBaseViewHolder>(ProgramBaseItemDiff) {

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (ReceiptItemType.values()[viewType]) {
            ReceiptItemType.INTAKE_PROGRAM_RECEIPT -> CreateProgramViewHolder(
                MedicineIntakeProgramItemBinding.inflate(
                    parent.inflater,
                    parent,
                    false
                ), onAction
            )
            ReceiptItemType.INTAKE_VIEW_PROGRAM -> ViewScheduleReceiptViewHolder(
                MedicineIntakeViewScheduleSaveBinding.inflate(
                    parent.inflater,
                    parent,
                    false
                ), onAction
            )
        } as ProgramBindingBaseViewHolder


    override fun onBindViewHolder(holder: ProgramBindingBaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int) = getItem(position).data.itemType.ordinal

}

object ProgramBaseItemDiff : DiffUtil.ItemCallback<ModuleItemDataWrapper<ReceiptModuleItem>>() {
    override fun areItemsTheSame(oldItem: ModuleItemDataWrapper<ReceiptModuleItem>, newItem: ModuleItemDataWrapper<ReceiptModuleItem>) =
        oldItem.data.id == newItem.data.id
    override fun areContentsTheSame(oldItem: ModuleItemDataWrapper<ReceiptModuleItem>, newItem: ModuleItemDataWrapper<ReceiptModuleItem>) =
        oldItem.data == newItem.data && oldItem.loadingState == newItem.loadingState
}