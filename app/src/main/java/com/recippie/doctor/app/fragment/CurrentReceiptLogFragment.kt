package com.recippie.doctor.app.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.recippie.doctor.app.databinding.CurrentReceiptLogFragmentBinding

class CurrentReceiptLogFragment : BaseBindingFragment<CurrentReceiptLogFragmentBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = CurrentReceiptLogFragmentBinding.inflate(inflater, container, false)


    companion object {
        fun newInstance() = CurrentReceiptLogFragment()
    }
}