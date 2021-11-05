package com.recippie.doctor.app.fragment

import android.app.Application
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
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
        val isNewFragment = savedInstanceState == null
        viewModel.loadReceiptPage(isNewFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                if (getInfoFromDynamicForm()) {
                    fragmentDelegate?.openFragment(ReceiptProgramFragment.newInstance(), ReceiptProgramFragment.TAG)
                    setAnimation(true)
                    setClickable(true)
                    isClicked = !isClicked
                }
            }
            fabAction.setOnClickListener {
                onAddButtonClicked()
            }
        }

        viewModel.recipeList.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        ReceiptFragmentBinding.inflate(inflater, container, false)

    private fun onAction(action: ReceiptActionEvent) {
    }

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

    private fun getInfoFromDynamicForm(): Boolean {
        val recyclerView = binding.rvRecipe
        val itemCount = recyclerView.adapter?.itemCount
        if (itemCount != null && itemCount > 0) {
            val list: MutableList<Receipt> = mutableListOf()
            for (i in 0 until itemCount) {
                val holder = recyclerView.findViewHolderForAdapterPosition(i)
                if (holder != null) {
                    val description = holder.itemView.findViewById<View>(R.id.et_description) as EditText
                    val during = holder.itemView.findViewById<View>(R.id.et_each_time) as EditText
                    val each = holder.itemView.findViewById<View>(R.id.et_during_time) as EditText
                    if (description.text.isNullOrEmpty() || each.text.isNullOrEmpty() || during.text.isNullOrEmpty()) {
                        showSnackBar(getString(R.string.not_empty_fields))
                        return false
                    }
                    list.add(Receipt(
                        description.text.toString(),
                        each.text.toString(),
                        during.text.toString())
                    )
                }
            }
            viewModel.saveFormReceipt(list)
        }
        return true
    }

    private fun showSnackBar(msg: String) {
        view?.let { Snackbar.make(it, msg, Snackbar.LENGTH_LONG) }
            ?.apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setTextColor(requireContext().getColor(R.color.white))
                }
                view.apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        background =
                            AppCompatResources.getDrawable(requireContext(), R.drawable.snackbar_rounded_rectangle)
                    }
                }
                show()
            }
    }

    companion object {
        const val TAG = "RecipeFragment"
        fun newInstance() = ReceiptFragment()
    }
}