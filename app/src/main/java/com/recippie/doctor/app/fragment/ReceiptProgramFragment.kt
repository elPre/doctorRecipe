package com.recippie.doctor.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.recippie.doctor.app.adapter.ProgramAdapter
import com.recippie.doctor.app.databinding.ReceiptProgramFragmentBinding
import com.recippie.doctor.app.event.ReceiptActionEvent
import com.recippie.doctor.app.viewmodel.ProgramViewModel

class ReceiptProgramFragment: BaseBindingFragment<ReceiptProgramFragmentBinding>() {

    private val adapter = ProgramAdapter(::onAction)
    private val viewModel: ProgramViewModel by viewModels()

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?)=
        ReceiptProgramFragmentBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isNewFragment = savedInstanceState == null
        viewModel.loadPage(isNewFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvReceiptProgram.adapter = this@ReceiptProgramFragment.adapter
        viewModel.moduleItemsLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun onAction(action: ReceiptActionEvent) {
    }

    companion object {
        const val TAG = "ReceiptProgramFragment"
        fun newInstance() = ReceiptProgramFragment()
    }
}