package com.recippie.doctor.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.recippie.doctor.app.adapter.ReceiptAdapter
import com.recippie.doctor.app.databinding.ReceiptFragmentBinding
import com.recippie.doctor.app.event.ReceiptActionEvent
import com.recippie.doctor.app.viewmodel.ReceiptViewModel

class ReceiptFragment : BaseBindingFragment<ReceiptFragmentBinding>() {

    private val adapter = ReceiptAdapter(::onAction)
    private val viewModel: ReceiptViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isNewFragment = savedInstanceState == null
        viewModel.loadRecipesItems(isNewFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            rvRecipe.layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            rvRecipe.adapter = adapter
            fabAdd.setOnClickListener {
                fragmentDelegate?.openFragment(ReceiptProgramFragment.newInstance(), ReceiptProgramFragment.TAG)
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

    companion object {
        const val TAG = "RecipeFragment"
        fun newInstance() = ReceiptFragment()
    }
}