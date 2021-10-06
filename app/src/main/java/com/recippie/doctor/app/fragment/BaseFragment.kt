package com.recippie.doctor.app.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.recippie.doctor.app.interfaces.BaseInterface

open class BaseFragment : Fragment() {

    private var fragmentDelegate: BaseInterface? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseInterface) {
            fragmentDelegate = context
        } else
            throw RuntimeException("Activity must be instance of BaseInterface")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onDetach() {
        fragmentDelegate = null
        super.onDetach()
    }

}

abstract class BaseBindingFragment<VB: ViewBinding> : BaseFragment() {

    private var _binding: ViewBinding? = null
    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB get() = _binding as VB

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflateBinding(inflater, container).let {
            _binding = it
            it.root
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.clearViews()
        _binding = null
    }

    protected open fun VB.clearViews() = Unit
}