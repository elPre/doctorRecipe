package com.recippie.doctor.app.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.annotation.LayoutRes
import kotlin.random.Random
import kotlin.random.nextLong

val Int.isNegative
    get() = this < 0

val Int.isNotNegative
    get() = this > -1

fun View.setBlinkActive(isActivated: Boolean) {
    if(isActivated) blink() else clearAnimation()
}

fun View.blink(
    times: Int = Animation.INFINITE,
    duration: Long = 300L,
    maxDuration: Long = 1000L,
    offset: Long = 300L,
    maxOffset: Long = 1500L,
    minAlpha: Float = 0.5f,
    maxAlpha: Float = 1.0f,
    repeatMode: Int = Animation.REVERSE
) {
    startAnimation(AlphaAnimation(minAlpha, maxAlpha).also {
        it.duration = Random.nextLong(duration..maxDuration)
        it.startOffset = Random.nextLong(offset..maxOffset)
        it.repeatMode = repeatMode
        it.repeatCount = times
        it.interpolator = AccelerateDecelerateInterpolator()
    })
}

val ViewGroup.inflater: LayoutInflater
    get() = LayoutInflater.from(context)