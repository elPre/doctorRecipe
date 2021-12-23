package com.recippie.doctor.app.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.recippie.doctor.app.fragment.CurrentReceiptLogFragment
import com.recippie.doctor.app.fragment.HistoryReceiptLogFragment
import com.recippie.doctor.app.fragment.Tabs

class CurrentHistoryAdapter(parent: Fragment, var tabList: List<Tabs>) : FragmentStateAdapter(parent) {

    override fun getItemCount() = tabList.size

    override fun createFragment(position: Int) = when (Tabs.values()[position]) {
        Tabs.CURRENT -> CurrentReceiptLogFragment.newInstance()
        Tabs.HISTORY -> HistoryReceiptLogFragment.newInstance()
    }

}