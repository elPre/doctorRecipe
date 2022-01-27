package com.recippie.doctor.app.holder

import com.google.android.gms.ads.AdRequest
import com.recippie.doctor.app.databinding.BannerHolderCurrentReceiptBinding
import com.recippie.doctor.app.event.CurrentHistoryActionEvent
import com.recippie.doctor.app.moduleitems.ModuleItemDataWrapper
import com.recippie.doctor.app.pojo.CurrentBanner

class CurrentAndHistoryBannerViewHolder(
    val binding: BannerHolderCurrentReceiptBinding,
    val onAction: (CurrentHistoryActionEvent) -> Unit,
) : CurrentAndHistoryBaseViewHolder<ModuleItemDataWrapper<CurrentBanner>, BannerHolderCurrentReceiptBinding>(binding) {

    override val ivLoading = null

    override fun bind(item: ModuleItemDataWrapper<CurrentBanner>) {
        // Create an ad request.
        val adRequest = AdRequest.Builder().build()
        // Start loading the ad in the background.
        binding.adView.loadAd(adRequest)
    }
}