package com.recippie.doctor.app.holder

import com.google.android.gms.ads.AdRequest
import com.recippie.doctor.app.databinding.BannerProgramBottomBinding
import com.recippie.doctor.app.event.ReceiptActionEvent
import com.recippie.doctor.app.pojo.ProgramBannerBottom
import com.recippie.doctor.app.pojo.ProgramBannerTop

class ProgramBannerBottomViewHolder (
    val binding: BannerProgramBottomBinding,
    val onAction: (ReceiptActionEvent) -> Unit,
) : BaseProgramViewHolder<ProgramBannerBottom>(binding.root) {

    override fun bind(item: ProgramBannerBottom) {
        // Create an ad request.
        val adRequest = AdRequest.Builder().build()
        // Start loading the ad in the background.
        binding.adView.loadAd(adRequest)
    }
}