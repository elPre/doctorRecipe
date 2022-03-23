package com.recippie.doctor.app.viewmodel

import android.app.Activity
import android.app.Application
import android.os.Handler
import androidx.lifecycle.*
import com.recippie.doctor.app.adapter.ReceiptAdapter
import com.recippie.doctor.app.bo.AlarmBO
import com.recippie.doctor.app.bo.BuildReceiptBO
import com.recippie.doctor.app.bo.IBuildReceiptBO
import com.recippie.doctor.app.interfaces.BaseReceipt
import com.recippie.doctor.app.pojo.Receipt
import com.recippie.doctor.app.repository.AlarmRepository
import com.recippie.doctor.app.repository.IAlarmRepository
import com.recippie.doctor.app.repository.IReceiptRepository
import com.recippie.doctor.app.repository.ReceiptRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.os.Looper




class ReceiptViewModel(val app: Application) : ViewModel(), BaseReceipt {

    private val receiptRepo: IReceiptRepository = ReceiptRepository(app)
    private val programRepo: IAlarmRepository = AlarmRepository(app)
    private val receiptBo: IBuildReceiptBO = BuildReceiptBO(receiptRepo, programRepo)
    private val _recipeList: MutableLiveData<MutableList<Receipt>> = MutableLiveData()
    val recipeList = _recipeList

    override fun loadReceiptPage(forceReload: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        loadReceipt()
    }

    override fun loadReceipt() = viewModelScope.launch(Dispatchers.IO) {
        receiptBo.getCurrentReceipt().let {
            if (it.isNotEmpty())
                _recipeList.postValue(it.toMutableList())
        }
    }

    override fun addReceipt(adapter: ReceiptAdapter) = viewModelScope.launch(Dispatchers.IO) {
        Handler(Looper.getMainLooper()).post { adapter.addData(Receipt()) }
    }

    override fun deleteReceipt(receiptDelete: Receipt) = viewModelScope.launch(Dispatchers.IO) {
        receiptBo.deleteReceipt(receiptDelete, AlarmBO(app))
    }

    override fun saveFormReceipt(list: List<Receipt>) = viewModelScope.launch(Dispatchers.IO) {
        receiptBo.saveReceipt(list.toList())
    }


    class Factory(private val app: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ReceiptViewModel(app) as T
        }
    }
}