package com.recippie.doctor.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.recippie.doctor.app.adapter.ProgramAdapter
import com.recippie.doctor.app.databinding.ReceiptProgramFragmentBinding
import com.recippie.doctor.app.event.ReceiptActionEvent

class ReceiptProgramFragment: BaseBindingFragment<ReceiptProgramFragmentBinding>() {

    private val adapter = ProgramAdapter(::onAction)

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?)=
        ReceiptProgramFragmentBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun onAction(action: ReceiptActionEvent) {
    }
}