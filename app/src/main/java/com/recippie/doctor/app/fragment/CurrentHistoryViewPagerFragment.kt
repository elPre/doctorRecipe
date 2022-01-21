package com.recippie.doctor.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import com.google.android.material.tabs.TabLayoutMediator
import com.recippie.doctor.app.R
import com.recippie.doctor.app.adapter.CurrentHistoryTabAdapter
import com.recippie.doctor.app.databinding.CurrentAndHistoryFragmentBinding

class CurrentHistoryViewPagerFragment : BaseBindingFragment<CurrentAndHistoryFragmentBinding>() {

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        CurrentAndHistoryFragmentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabList = Tabs.values().toList()
        val pagerAdapter = CurrentHistoryTabAdapter(this@CurrentHistoryViewPagerFragment, tabList)
        with(binding) {
            viewPagerHistory.apply { adapter = pagerAdapter }
            TabLayoutMediator(tabLayout, viewPagerHistory) { tab, pos ->
                tab.text = getString(tabList[pos].currentReceiptLog)
            }.attach()
            arguments?.run {
                if (this.getSerializable(SELECTED_TAB) == Tabs.HISTORY) {
                    viewPagerHistory.currentItem = HISTORY_TAB
                }
            }
        }
    }

    companion object {
        const val TAG = "CurrentHistoryViewPagerFragment"
        private const val SELECTED_TAB = "selectedTab"
        private const val HISTORY_TAB = 1
        fun newInstance(selectedTab: Tabs? = null) = CurrentHistoryViewPagerFragment().apply {
            arguments = bundleOf(SELECTED_TAB to selectedTab)
        }
    }
}

enum class Tabs(@StringRes val currentReceiptLog: Int) {
    CURRENT(R.string.current_receipt_log),
    HISTORY(R.string.history_receipt_log),
}