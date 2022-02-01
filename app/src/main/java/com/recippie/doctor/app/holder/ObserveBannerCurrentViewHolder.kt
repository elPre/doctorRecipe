package com.recippie.doctor.app.holder

import com.google.android.gms.ads.AdRequest
import com.recippie.doctor.app.databinding.BannerHolderCurrentReceiptBinding
import com.recippie.doctor.app.databinding.BannerProgramBottomBinding
import com.recippie.doctor.app.databinding.BannerProgramTopBinding
import com.recippie.doctor.app.event.ReceiptActionEvent
import com.recippie.doctor.app.pojo.ObserveBannerCurrentTop
import com.recippie.doctor.app.pojo.ProgramBannerTop

class ObserveBannerCurrentViewHolder (
    val binding: BannerHolderCurrentReceiptBinding,
    val onAction: (ReceiptActionEvent) -> Unit,
) : BaseProgramViewHolder<ObserveBannerCurrentTop>(binding.root) {

    override fun bind(item: ObserveBannerCurrentTop) {
        // Create an ad request.
        val adRequest = AdRequest.Builder().build()
        // Start loading the ad in the background.
        binding.adView.loadAd(adRequest)
    }
}