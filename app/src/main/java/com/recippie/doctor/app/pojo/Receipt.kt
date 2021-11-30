package com.recippie.doctor.app.pojo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Receipt(
    val numReceipt: Long? = null,
    val description: String = "",
    val eachTime: String = "",
    val duringTime: String = "",
) : Parcelable