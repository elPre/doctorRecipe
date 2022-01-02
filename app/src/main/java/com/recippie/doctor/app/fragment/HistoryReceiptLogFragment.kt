package com.recippie.doctor.app.fragment

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.recippie.doctor.app.adapter.CurrentAndHistoryAdapter
import com.recippie.doctor.app.databinding.HistoryReceiptLogFragmentBinding
import com.recippie.doctor.app.event.CurrentHistoryActionEvent
import com.recippie.doctor.app.viewmodel.CurrentAndHistoryReceiptViewModel

class HistoryReceiptLogFragment : BaseBindingFragment<HistoryReceiptLogFragmentBinding>() {

    private val adapter = CurrentAndHistoryAdapter(::onAction)
    private val viewModel : CurrentAndHistoryReceiptViewModel by viewModels {
        CurrentAndHistoryReceiptViewModel.Factory(requireContext().applicationContext as Application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadCurrentReceipt()
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = HistoryReceiptLogFragmentBinding.inflate(inflater, container, false)


    private fun onAction(action: CurrentHistoryActionEvent) { }

    companion object {
        fun newInstance() = HistoryReceiptLogFragment()
    }
}