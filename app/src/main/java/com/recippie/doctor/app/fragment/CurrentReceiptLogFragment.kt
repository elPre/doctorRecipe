package com.recippie.doctor.app.fragment

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.recippie.doctor.app.adapter.CurrentAndHistoryAdapter
import com.recippie.doctor.app.databinding.CurrentReceiptLogFragmentBinding
import com.recippie.doctor.app.event.CurrentHistoryActionEvent
import com.recippie.doctor.app.viewmodel.CurrentReceiptViewModel

class CurrentReceiptLogFragment : BaseBindingFragment<CurrentReceiptLogFragmentBinding>() {

    private val adapter = CurrentAndHistoryAdapter(::onAction)
    private val viewModel : CurrentReceiptViewModel by viewModels {
        CurrentReceiptViewModel.Factory(requireContext().applicationContext as Application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadCurrentReceipt()
    }

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        CurrentReceiptLogFragmentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCurrentHistory.adapter = this@CurrentReceiptLogFragment.adapter
        viewModel.moduleItemsLiveData.observe(::getLifecycle) { moduleItems ->
            adapter.submitList(moduleItems.toList())
        }
    }

    private fun onAction(action: CurrentHistoryActionEvent) { }

    companion object {
        fun newInstance() = CurrentReceiptLogFragment()
    }
}