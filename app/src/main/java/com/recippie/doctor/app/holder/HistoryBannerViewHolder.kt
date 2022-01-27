package com.recippie.doctor.app.holder

import com.google.android.gms.ads.AdRequest
import com.recippie.doctor.app.databinding.BannerHolderHistoryReceiptBinding
import com.recippie.doctor.app.event.CurrentHistoryActionEvent
import com.recippie.doctor.app.moduleitems.ModuleItemDataWrapper
import com.recippie.doctor.app.pojo.CurrentBanner
import com.recippie.doctor.app.pojo.HistoryBanner

class HistoryBannerViewHolder(
    val binding: BannerHolderHistoryReceiptBinding,
    val onAction: (CurrentHistoryActionEvent) -> Unit,
) : HistoryBaseViewHolder<ModuleItemDataWrapper<HistoryBanner>, BannerHolderHistoryReceiptBinding>(binding) {

    override val ivLoading = null

    override fun bind(item: ModuleItemDataWrapper<HistoryBanner>) {
        // Create an ad request.
        val adRequest = AdRequest.Builder().build()
        // Start loading the ad in the background.
        binding.adView.loadAd(adRequest)
    }
}