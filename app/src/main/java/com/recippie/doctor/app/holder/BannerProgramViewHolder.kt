package com.recippie.doctor.app.holder

import com.google.android.gms.ads.AdRequest
import com.recippie.doctor.app.databinding.BannerHolderBinding
import com.recippie.doctor.app.event.ReceiptActionEvent
import com.recippie.doctor.app.moduleitems.ModuleItemDataWrapper
import com.recippie.doctor.app.pojo.CreateProgram

class BannerProgramViewHolder (
    val binding: BannerHolderBinding,
    val onAction: (ReceiptActionEvent) -> Unit,
) : ReceiptBaseViewHolder<ModuleItemDataWrapper<CreateProgram>, BannerHolderBinding>(
    binding
) {

    override val ivLoading = null

    override fun bind(item: ModuleItemDataWrapper<CreateProgram>) {
        // Create an ad request.
        val adRequest = AdRequest.Builder().build()
        // Start loading the ad in the background.
        binding.adView.loadAd(adRequest)
    }
}