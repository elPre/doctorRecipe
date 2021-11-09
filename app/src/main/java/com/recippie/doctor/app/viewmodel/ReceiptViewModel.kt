package com.recippie.doctor.app.viewmodel

import androidx.lifecycle.*
import com.recippie.doctor.app.adapter.ReceiptAdapter
import com.recippie.doctor.app.bo.IBuildReceiptBO
import com.recippie.doctor.app.interfaces.BaseReceipt
import com.recippie.doctor.app.pojo.Receipt
import kotlinx.coroutines.launch

class ReceiptViewModel(private val receiptBo: IBuildReceiptBO) : ViewModel(), BaseReceipt {

    private val _recipeList: MutableLiveData<MutableList<Receipt>> = MutableLiveData()
    val recipeList = _recipeList

    override fun loadReceiptPage(forceReload: Boolean) = viewModelScope.launch {
        if (!forceReload) {
            return@launch
        }
        loadReceipt()
    }

    override fun loadReceipt() = viewModelScope.launch {
        receiptBo.getCurrentReceipt().let {
            if (it.isNotEmpty())
                _recipeList.postValue(it.toMutableList())
        }
    }

    override fun addReceipt(adapter: ReceiptAdapter) = viewModelScope.launch {
        adapter.addData(Receipt())
    }

    override fun deleteReceipt() = viewModelScope.launch {

    }

    override fun saveFormReceipt(list: List<Receipt>) = viewModelScope.launch {
        receiptBo.saveReceipt(list.toList())
    }


    class Factory(private val receiptBO: IBuildReceiptBO) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ReceiptViewModel(receiptBO) as T
        }
    }
}