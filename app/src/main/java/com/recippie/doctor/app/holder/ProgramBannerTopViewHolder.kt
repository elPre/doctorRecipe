package com.recippie.doctor.app.holder

import com.google.android.gms.ads.AdRequest
import com.recippie.doctor.app.databinding.BannerProgramTopBinding
import com.recippie.doctor.app.event.ReceiptActionEvent
import com.recippie.doctor.app.pojo.ProgramBannerTop

class ProgramBannerTopViewHolder (
    val binding: BannerProgramTopBinding,
    val onAction: (ReceiptActionEvent) -> Unit,
) : BaseProgramViewHolder<ProgramBannerTop>(binding.root) {

    override fun bind(item: ProgramBannerTop) {
        // Create an ad request.
        val adRequest = AdRequest.Builder().build()
        // Start loading the ad in the background.
        binding.adView.loadAd(adRequest)
    }
}