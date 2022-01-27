package com.recippie.doctor.app.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.ads.MobileAds
import com.recippie.doctor.app.R
import com.recippie.doctor.app.databinding.ActivityMainBinding
import com.recippie.doctor.app.fragment.CurrentHistoryViewPagerFragment
import com.recippie.doctor.app.fragment.ReceiptFragment
import com.recippie.doctor.app.fragment.Tabs
import com.recippie.doctor.app.interfaces.BaseInterface
import com.recippie.doctor.app.util.NotificationUtils

class MainActivity : BaseBindingActivity<ActivityMainBinding>(), BaseInterface {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate

    override fun onStart() {
        super.onStart()
        // Initialize the Mobile Ads SDK with an AdMob App ID.
        MobileAds.initialize(this) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.run {
            if (this.extras?.getInt(NotificationUtils.EXTRA_OPEN_NOTIFICATION) == NotificationUtils.OPEN_NOTIFICATION_EXTRA_VALUE) {
                openFragment(ReceiptFragment.newInstance(), ReceiptFragment.TAG)
                openFragment(CurrentHistoryViewPagerFragment.newInstance(Tabs.HISTORY), CurrentHistoryViewPagerFragment.TAG)
                intent.removeExtra(NotificationUtils.EXTRA_OPEN_NOTIFICATION)
                return
            }
        }
        if (savedInstanceState == null) {
            openFragment(ReceiptFragment.newInstance(), ReceiptFragment.TAG)
        }
    }

    override fun openFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .replace(R.id.content_container, fragment)
            .addToBackStack(tag).commit()
    }

    override fun popFragment() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == ONE_FRAGMENT
            && supportFragmentManager.fragments[0] is ReceiptFragment)
            finish()
        else
            super.onBackPressed()
    }

    companion object {
        private const val ONE_FRAGMENT = 1
    }
}