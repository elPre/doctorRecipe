package com.recippie.doctor.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.recippie.doctor.app.databinding.ReceiptFlatFragmentBinding


class ReceiptFlatFragment : BaseBindingFragment<ReceiptFlatFragmentBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        const val TAG = "RecipeFlatFragment"
        fun newInstance() = ReceiptFlatFragment()
    }

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?)=
        ReceiptFlatFragmentBinding.inflate(inflater, container, false)
}