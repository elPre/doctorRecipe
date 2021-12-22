package com.recippie.doctor.app.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.recippie.doctor.app.adapter.ReceiptAdapter
import com.recippie.doctor.app.bo.BuildReceiptBO
import com.recippie.doctor.app.bo.IBuildReceiptBO
import com.recippie.doctor.app.interfaces.BaseReceipt
import com.recippie.doctor.app.pojo.Receipt
import com.recippie.doctor.app.repository.AlarmRepository
import com.recippie.doctor.app.repository.IAlarmRepository
import com.recippie.doctor.app.repository.IReceiptRepository
import com.recippie.doctor.app.repository.ReceiptRepository
import kotlinx.coroutines.launch

class ReceiptViewModel(app: Application) : ViewModel(), BaseReceipt {

    private val receiptRepo: IReceiptRepository = ReceiptRepository(app)
    private val programRepo: IAlarmRepository = AlarmRepository(app)
    private val receiptBo: IBuildReceiptBO = BuildReceiptBO(receiptRepo, programRepo)
    private val _recipeList: MutableLiveData<MutableList<Receipt>> = MutableLiveData()
    val recipeList = _recipeList

    override fun loadReceiptPage(forceReload: Boolean) = viewModelScope.launch {
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

    override fun deleteReceipt(receiptDelete: Receipt) = viewModelScope.launch {
        receiptBo.deleteReceipt(receiptDelete)
    }

    override fun saveFormReceipt(list: List<Receipt>) = viewModelScope.launch {
        receiptBo.saveReceipt(list.toList())
    }


    class Factory(private val app: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ReceiptViewModel(app) as T
        }
    }
}