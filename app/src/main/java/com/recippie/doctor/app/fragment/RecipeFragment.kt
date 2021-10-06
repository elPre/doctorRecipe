package com.recippie.doctor.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.recippie.doctor.app.adapter.RecipeAdapter
import com.recippie.doctor.app.databinding.RecipeFragmentBinding
import com.recippie.doctor.app.event.RecipeActionEvent
import com.recippie.doctor.app.viewmodel.RecipeViewModel

class RecipeFragment : BaseBindingFragment<RecipeFragmentBinding>() {

    private val adapter = RecipeAdapter(::onAction)
    private val viewModel: RecipeViewModel by viewModels()

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
        }
        viewModel.recipeList.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        RecipeFragmentBinding.inflate(inflater, container, false)

    private fun onAction(action: RecipeActionEvent) {

    }

    companion object {
        const val TAG = "RecipeFragment"
        fun newInstance() = RecipeFragment()
    }
}