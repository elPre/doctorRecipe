package com.recippie.doctor.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import com.google.android.material.tabs.TabLayoutMediator
import com.recippie.doctor.app.R
import com.recippie.doctor.app.adapter.CurrentHistoryAdapter
import com.recippie.doctor.app.databinding.CurrentAndHistoryFragmentBinding

class CurrentHistoryViewPagerFragment : BaseBindingFragment<CurrentAndHistoryFragmentBinding>() {

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        CurrentAndHistoryFragmentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabList = Tabs.values().toList()
        val pagerAdapter = CurrentHistoryAdapter(this@CurrentHistoryViewPagerFragment, tabList)
        with(binding) {
            viewPagerHistory.apply { adapter = pagerAdapter }
            TabLayoutMediator(tabLayout, viewPagerHistory) { tab, pos ->
                tab.text = getString(tabList[pos].currentReceiptLog)
            }.attach()
        }
    }

    companion object {
        const val TAG = "CurrentHistoryViewPagerFragment"
        fun newInstance() = CurrentHistoryViewPagerFragment()
    }
}

enum class Tabs(@StringRes val currentReceiptLog: Int) {
    CURRENT(R.string.current_receipt_log),
    HISTORY(R.string.history_receipt_log)
}