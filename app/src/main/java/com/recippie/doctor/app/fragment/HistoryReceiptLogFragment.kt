package com.recippie.doctor.app.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.recippie.doctor.app.databinding.HistoryReceiptLogFragmentBinding

class HistoryReceiptLogFragment : BaseBindingFragment<HistoryReceiptLogFragmentBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = HistoryReceiptLogFragmentBinding.inflate(inflater, container, false)

    companion object {
        fun newInstance() = HistoryReceiptLogFragment()
    }
}