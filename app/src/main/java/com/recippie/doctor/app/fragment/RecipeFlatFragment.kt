package com.recippie.doctor.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.recippie.doctor.app.databinding.RecipeFlatFragmentBinding

class RecipeFlatFragment : BaseBindingFragment<RecipeFlatFragmentBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        const val TAG = "RecipeFlatFragment"
        fun newInstance() = RecipeFlatFragment()
    }

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?)=
        RecipeFlatFragmentBinding.inflate(inflater, container, false)
}