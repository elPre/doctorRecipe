package com.recippie.doctor.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.recippie.doctor.app.adapter.ProgramAdapter
import com.recippie.doctor.app.databinding.ReceiptProgramFragmentBinding
import com.recippie.doctor.app.event.ReceiptActionEvent
import com.recippie.doctor.app.viewmodel.ProgramViewModel

class ReceiptProgramFragment : BaseBindingFragment<ReceiptProgramFragmentBinding>() {

    private val adapter = ProgramAdapter(::onAction)
    private val viewModel: ProgramViewModel by viewModels()

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        ReceiptProgramFragmentBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isNewFragment = savedInstanceState == null
        viewModel.loadPage(isNewFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvReceiptProgram.adapter = this@ReceiptProgramFragment.adapter
        viewModel.moduleItemsLiveData.observe(::getLifecycle) { moduleItems ->
            adapter.submitList(moduleItems.toList())
        }
    }

    private fun onAction(action: ReceiptActionEvent) {
        when (action) {
            ReceiptActionEvent.SaveProgram -> Unit
            ReceiptActionEvent.OpenCalendar -> showDatePicker()
            ReceiptActionEvent.OpenClock -> showTimePicker()
            ReceiptActionEvent.ProgramSchedule -> viewModel.loadSchedule()
            else -> Unit
        }
    }

    private fun showTimePicker() {
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Start Alarm Time")
            .build().apply {
                addOnPositiveButtonClickListener {
                    onTimeSelected(this)
                }
            }
        timePicker.show(childFragmentManager, TAG)
    }

    private fun onTimeSelected(timePicker: MaterialTimePicker) {
        viewModel.setTime(timePicker)
    }


    private fun showDatePicker() {
        MaterialDatePicker.Builder.datePicker().setSelection(System.currentTimeMillis()).build().apply {
            addOnPositiveButtonClickListener { dateInMillis -> onDateSelected(dateInMillis) }
        }.show(childFragmentManager, TAG)
    }


    private fun onDateSelected(dateTimeStampInMillis: Long) {
        viewModel.setDate(dateTimeStampInMillis)
    }

    companion object {
        const val TAG = "ReceiptProgramFragment"
        fun newInstance() = ReceiptProgramFragment()
    }
}