package com.recippie.doctor.app.fragment

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.recippie.doctor.app.R
import com.recippie.doctor.app.adapter.ReceiptAdapter
import com.recippie.doctor.app.bo.BuildReceiptBO
import com.recippie.doctor.app.databinding.ReceiptFragmentBinding
import com.recippie.doctor.app.event.ReceiptActionEvent
import com.recippie.doctor.app.pojo.Receipt
import com.recippie.doctor.app.repository.ReceiptRepository
import com.recippie.doctor.app.util.SwipeToDeleteCallback
import com.recippie.doctor.app.viewmodel.ReceiptViewModel

class ReceiptFragment : BaseBindingFragment<ReceiptFragmentBinding>() {

    private val adapter = ReceiptAdapter(::onAction)
    private val viewModel: ReceiptViewModel by viewModels {
        ReceiptViewModel.Factory(
            BuildReceiptBO(ReceiptRepository(requireContext().applicationContext as Application))
        )
    }

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_open) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_close) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.to_bottom) }

    private var isClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.recipeList.observe(::getLifecycle) {
            adapter.setData(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadReceiptPage()
        with(binding) {
            rvRecipe.layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(requireContext(), adapter))
            itemTouchHelper.attachToRecyclerView(rvRecipe)
            rvRecipe.adapter = adapter
            fabCreate.setOnClickListener {
                viewModel.addReceipt(adapter)
                setAnimation(true)
                setClickable(true)
                isClicked = !isClicked
            }
            fabProgram.setOnClickListener {
                val infoForm = getInfoFromDynamicForm()
                if (infoForm.first) {
                    setAnimation(true)
                    setClickable(true)
                    isClicked = !isClicked
                    fragmentDelegate?.openFragment(ReceiptProgramFragment.newInstance(infoForm.second), ReceiptProgramFragment.TAG)
                }
            }
            fabAction.setOnClickListener {
                onAddButtonClicked()
            }
        }
    }

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        ReceiptFragmentBinding.inflate(inflater, container, false)

    private fun onAction(action: ReceiptActionEvent) { }

    private fun onAddButtonClicked() {
        setVisibility(isClicked)
        setAnimation(isClicked)
        setClickable(isClicked)
        isClicked = !isClicked
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.fabCreate.isVisible = true
            binding.fabProgram.isVisible = true
        } else {
            binding.fabCreate.isVisible = false
            binding.fabProgram.isVisible = false
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding.fabCreate.startAnimation(fromBottom)
            binding.fabProgram.startAnimation(fromBottom)
            binding.fabAction.startAnimation(rotateOpen)
        } else {
            binding.fabCreate.startAnimation(toBottom)
            binding.fabProgram.startAnimation(toBottom)
            binding.fabAction.startAnimation(rotateClose)
        }
    }

    private fun setClickable(clicked: Boolean) {
        if (!clicked) {
            binding.fabCreate.isClickable = true
            binding.fabProgram.isClickable = true
        } else {
            binding.fabCreate.isClickable = false
            binding.fabProgram.isClickable = false
        }
    }

    private fun getInfoFromDynamicForm(): Pair<Boolean, MutableList<Receipt>> {
        val recyclerView = binding.rvRecipe
        val itemCount = recyclerView.adapter?.itemCount
        if (itemCount != null && itemCount > 0) {
            val list: MutableList<Receipt> = mutableListOf()
            for (i in 0 until itemCount) {
                val holder = recyclerView.findViewHolderForAdapterPosition(i)
                if (holder != null) {
                    val description = holder.itemView.findViewById<View>(R.id.et_description) as EditText
                    val each = holder.itemView.findViewById<View>(R.id.et_each_time) as EditText
                    val during = holder.itemView.findViewById<View>(R.id.et_during_time) as EditText
                    val numReceipt = holder.itemView.findViewById<View>(R.id.tv_receipt_number) as TextView
                    val numMedicine = holder.itemView.findViewById<View>(R.id.tv_num_medicine) as TextView
                    when {
                        description.text.isNullOrEmpty() || each.text.isNullOrEmpty() || during.text.isNullOrEmpty() -> {
                            showSnackbar(getString(R.string.not_empty_fields))
                            return Pair(false, mutableListOf())
                        }
                        each.text.toString().toInt() > MAX_HRS ||  each.text.toString().toInt() <= MIN_VALUE -> {
                            showSnackbar(getString(R.string.receipt_validation_max_hrs))
                            return Pair(false, mutableListOf())
                        }
                        during.text.toString().toInt() > MAX_DAYS || during.text.toString().toInt() <= MIN_VALUE -> {
                            showSnackbar(getString(R.string.receipt_validation_max_days))
                            return Pair(false, mutableListOf())
                        }
                    }

                    list.add(
                        Receipt(
                            numReceipt = if (numReceipt.text.isNotBlank()) numReceipt.text.toString().toLong() else null,
                            description = description.text.toString(),
                            eachTime = each.text.toString(),
                            duringTime = during.text.toString(),
                            numMedicine = if (numMedicine.text.isNotBlank()) numMedicine.text.toString().toInt() else 0
                        )
                    )
                }
            }
            viewModel.saveFormReceipt(list.toList())
            return Pair(first = true, second = list)
        }
        showSnackbar(getString(R.string.receipt_empty))
        return Pair(false, mutableListOf())
    }

    companion object {
        const val TAG = "RecipeFragment"
        private const val MAX_HRS = 24
        private const val MAX_DAYS = 365
        private const val MIN_VALUE = 0
        fun newInstance() = ReceiptFragment()
    }
}