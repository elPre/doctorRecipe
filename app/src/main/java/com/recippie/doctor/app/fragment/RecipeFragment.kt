package com.recippie.doctor.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.recippie.doctor.app.adapter.RecipeAdapter
import com.recippie.doctor.app.databinding.RecipeFragmentBinding

class RecipeFragment: BaseBindingFragment<RecipeFragmentBinding>() {

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        RecipeFragmentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    companion object {
        const val TAG = "RecipeFragment"
        fun newInstance() = RecipeFragment()
    }

}