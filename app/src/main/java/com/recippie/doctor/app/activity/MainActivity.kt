package com.recippie.doctor.app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import com.recippie.doctor.app.interfaces.BaseInterface
import com.recippie.doctor.app.R
import com.recippie.doctor.app.fragment.RecipeFragment

class MainActivity : AppCompatActivity(), BaseInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openFragment(RecipeFragment.newInstance(), RecipeFragment.TAG)
    }

    override fun openFragment(fragment: Fragment, tag: String) {
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) return
        supportFragmentManager.beginTransaction().replace(R.id.content_container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(tag).commit()
    }
}