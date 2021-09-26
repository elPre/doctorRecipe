package com.recippie.doctor.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.recippie.doctor.app.adapter.RecipeAdapter
import com.recippie.doctor.app.databinding.RecipeFragmentBinding
import com.recippie.doctor.app.event.RecipeActionEvent
import com.recippie.doctor.app.viewmodel.RecipeViewModel

class RecipeFragment: BaseBindingFragment<RecipeFragmentBinding>() {

    //private val adapter = RecipeAdapter(::onAction)
    //private val viewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewModel.loadRecipes()
    }

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        RecipeFragmentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        viewModel.recipeList.observe(viewLifecycleOwner) {
//            adapter.setData(it)
//        }
    }

    private fun onAction(action: RecipeActionEvent) {

    }

    companion object {
        const val TAG = "RecipeFragment"
        fun newInstance() = RecipeFragment()
    }

}