package com.recippie.doctor.app.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.recippie.doctor.app.R
import com.recippie.doctor.app.databinding.ActivityMainBinding
import com.recippie.doctor.app.fragment.ReceiptFragment
import com.recippie.doctor.app.interfaces.BaseInterface

class MainActivity : BaseBindingActivity<ActivityMainBinding>(), BaseInterface {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding = ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if( savedInstanceState == null ) {
            openFragment(ReceiptFragment.newInstance(), ReceiptFragment.TAG)
        }
    }

    override fun openFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction().add(R.id.content_container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(tag).commit()
    }
}