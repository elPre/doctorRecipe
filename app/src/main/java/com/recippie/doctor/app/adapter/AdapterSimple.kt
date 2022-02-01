package com.recippie.doctor.app.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.recippie.doctor.app.databinding.*
import com.recippie.doctor.app.event.ReceiptActionEvent
import com.recippie.doctor.app.holder.*
import com.recippie.doctor.app.interfaces.IAdapter
import com.recippie.doctor.app.pojo.ReceiptItemType
import com.recippie.doctor.app.pojo.ReceiptModuleItem
import com.recippie.doctor.app.util.inflater

class AdapterSimple(
    private val onAction: (ReceiptActionEvent) -> Unit
) : ListAdapter<ReceiptModuleItem, BaseProgramViewHolder<ReceiptModuleItem>>(ProgramItemDiff), IAdapter<ReceiptModuleItem> {

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (ReceiptItemType.values()[viewType]) {

        ReceiptItemType.PROGRAM_BANNER_TOP -> ProgramBannerTopViewHolder(
            BannerProgramTopBinding.inflate(
                parent.inflater,
                parent,
                false
            ), onAction
        )

        ReceiptItemType.PROGRAM_BANNER_BOTTOM -> ProgramBannerBottomViewHolder(
            BannerProgramBottomBinding.inflate(
                parent.inflater,
                parent,
                false
            ), onAction
        )

        ReceiptItemType.INTAKE_PROGRAM_RECEIPT -> CreateProgramViewHolder(
            MedicineIntakeProgramItemBinding.inflate(
                parent.inflater,
                parent,
                false
            ), onAction
        )

        ReceiptItemType.HEADER_INFO_LIST -> HeaderInfoViewHolder(
            HeaderInfoReceiptItemBinding.inflate(
                parent.inflater,
                parent,
                false
            ), onAction,
        )

        ReceiptItemType.INTAKE_VIEW_PROGRAM -> ViewScheduleReceiptItemViewHolder(
            MedicineIntakeScheduleItemBinding.inflate(
                parent.inflater,
                parent,
                false
            ), onAction,
        )

        ReceiptItemType.INTAKE_SAVE_BTN -> ProgramSaveViewHolder(
            ProgramSaveBtnBinding.inflate(
                parent.inflater,
                parent,
                false
            ), onAction,
        )

        ReceiptItemType.OBSERVE_BANNER_CURRENT -> ObserveBannerCurrentViewHolder(
            BannerHolderCurrentReceiptBinding.inflate(parent.inflater,
                parent,
                false
            ), onAction,
        )

        ReceiptItemType.OBSERVE_BANNER_HISTORY -> ObserveBannerHistoryViewHolder(
            BannerHolderHistoryReceiptBinding.inflate(parent.inflater,
                parent,
                false
            ), onAction,
        )


    } as BaseProgramViewHolder<ReceiptModuleItem>

    override fun onBindViewHolder(holder: BaseProgramViewHolder<ReceiptModuleItem>, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = getItem(position).itemType.ordinal

    override fun getItemAt(position: Int) = currentList.getOrNull(position)

}

object ProgramItemDiff : DiffUtil.ItemCallback<ReceiptModuleItem>() {
    override fun areItemsTheSame(oldItem: ReceiptModuleItem, newItem: ReceiptModuleItem) =
        oldItem.itemType == newItem.itemType
    override fun areContentsTheSame(oldItem: ReceiptModuleItem, newItem: ReceiptModuleItem) =
        oldItem == newItem
}