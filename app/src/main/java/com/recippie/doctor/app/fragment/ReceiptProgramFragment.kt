package com.recippie.doctor.app.fragment

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.recippie.doctor.app.R
import com.recippie.doctor.app.adapter.ProgramAdapter
import com.recippie.doctor.app.bo.BuildReceiptBO
import com.recippie.doctor.app.databinding.ReceiptProgramFragmentBinding
import com.recippie.doctor.app.event.ReceiptActionEvent
import com.recippie.doctor.app.pojo.Receipt
import com.recippie.doctor.app.repository.ReceiptRepository
import com.recippie.doctor.app.viewmodel.ProgramViewModel
import java.time.LocalDateTime
import java.time.LocalTime

class ReceiptProgramFragment : BaseBindingFragment<ReceiptProgramFragmentBinding>() {

    private val adapter = ProgramAdapter(::onAction)
    private val viewModel: ProgramViewModel by viewModels {
        ProgramViewModel.Factory(
            BuildReceiptBO(ReceiptRepository(requireContext().applicationContext as Application))
        )
    }

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        ReceiptProgramFragmentBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val listReceipt = it.getParcelableArrayList<Receipt>(LIST_INFO)
            listReceipt?.run {
                viewModel.setReceiptList(this)
            }
        }
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
            ReceiptActionEvent.NotEmptyFieldsAllowed -> showSnackbar(getString(R.string.not_empty_fields_program))
        }
    }

    private fun showTimePicker() {
        val time: LocalTime = LocalTime.now();
        MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(time.hour)
            .setMinute(time.minute)
            .setTitleText(getString(R.string.time_picker_title))
            .build().apply {
                addOnPositiveButtonClickListener {
                    onTimeSelected(this)
                }
            }.show(childFragmentManager, TAG)
    }

    private fun onTimeSelected(timePicker: MaterialTimePicker) {
        viewModel.setTime(timePicker)
    }

    private fun showDatePicker() {
        MaterialDatePicker.Builder
            .datePicker()
            .setTitleText(getString(R.string.date_picker_title))
            .setSelection(System.currentTimeMillis())
            .build().apply {
                addOnPositiveButtonClickListener { dateInMillis -> onDateSelected(dateInMillis) }
            }.show(childFragmentManager, TAG)
    }

    private fun onDateSelected(dateTimeStampInMillis: Long) {
        viewModel.setDate(dateTimeStampInMillis)
    }

    companion object {
        const val TAG = "ReceiptProgramFragment"
        private const val LIST_INFO = "list-info"
        fun newInstance(listReceipt: List<Receipt>? = null) = ReceiptProgramFragment().apply {
            arguments = bundleOf(
                LIST_INFO to listReceipt
            )
        }
    }
}