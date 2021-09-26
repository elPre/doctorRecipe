package com.recippie.doctor.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.recippie.doctor.app.R

class RecipeFlatFragment: BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return return inflater.inflate(R.layout.recipe_item_flat, container, false)
    }

    companion object {
        const val TAG = "RecipeFlatFragment"
        fun newInstance() = RecipeFlatFragment()
    }
}