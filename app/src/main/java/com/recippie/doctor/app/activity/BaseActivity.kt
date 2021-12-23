package com.recippie.doctor.app.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseActivity : AppCompatActivity() {

    var isSaveInstanceStateCalled: Boolean = false
    protected open val useDefaultOrientationHandling = true

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        isSaveInstanceStateCalled = false
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        isSaveInstanceStateCalled = true
    }

    override fun onResume() {
        super.onResume()
        isSaveInstanceStateCalled = false
    }
}

abstract class BaseBindingActivity<VB : ViewBinding> : BaseActivity() {

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> VB

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.clearViews()
        _binding = null
    }

    protected open fun VB.clearViews() = Unit
}
