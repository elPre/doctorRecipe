package com.recippie.doctor.app.fragment

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.recippie.doctor.app.adapter.HistoryAdapter
import com.recippie.doctor.app.databinding.HistoryReceiptLogFragmentBinding
import com.recippie.doctor.app.event.CurrentHistoryActionEvent
import com.recippie.doctor.app.viewmodel.HistoryReceiptViewModel

class HistoryReceiptLogFragment : BaseBindingFragment<HistoryReceiptLogFragmentBinding>() {

    private val adapter = HistoryAdapter(::onAction)
    private val viewModel : HistoryReceiptViewModel by viewModels {
        HistoryReceiptViewModel.Factory(requireContext().applicationContext as Application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadHistoryReceipts()
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = HistoryReceiptLogFragmentBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvHistory.adapter = this@HistoryReceiptLogFragment.adapter
        viewModel.moduleItemsLiveData.observe(::getLifecycle) { moduleItems ->
            adapter.submitList(moduleItems.toList())
        }
    }

    private fun onAction(action: CurrentHistoryActionEvent) { }

    companion object {
        fun newInstance() = HistoryReceiptLogFragment()
    }
}