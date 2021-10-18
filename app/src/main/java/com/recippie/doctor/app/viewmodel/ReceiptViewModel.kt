package com.recippie.doctor.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recippie.doctor.app.adapter.ReceiptAdapter
import com.recippie.doctor.app.interfaces.BaseReceipt
import com.recippie.doctor.app.pojo.Receipt
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ReceiptViewModel : ViewModel(), BaseReceipt {

    private val _recipeList: MutableLiveData<MutableList<Receipt>> = MutableLiveData()
    val recipeList = _recipeList

//    fun loadRecipesItems(forceReload: Boolean = true) = viewModelScope.launch {
//        if (!forceReload) {
//            return@launch
//        }
//        async { loadRecipes() }
//    }
//
//    private fun loadRecipes() = viewModelScope.launch {
//        val list: MutableList<Receipt> = mutableListOf()
//        for (i in 1..20) {
//            list.add(Receipt("description $i", "8 hrs", "8 dias"))
//        }
//        _recipeList.postValue(list)
//    }

    override fun loadReceiptPage(forceReload: Boolean)= viewModelScope.launch {

    }

    override fun loadReceipt()= viewModelScope.launch {

    }

    override fun addReceipt(adapter: ReceiptAdapter)= viewModelScope.launch {
        adapter.addData(Receipt())
    }

    override fun deleteReceipt()= viewModelScope.launch {

    }
}