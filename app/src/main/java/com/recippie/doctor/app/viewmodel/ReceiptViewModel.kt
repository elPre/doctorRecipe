package com.recippie.doctor.app.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.room.Room
import com.recippie.doctor.app.adapter.ReceiptAdapter
import com.recippie.doctor.app.data.ReceiptDao
import com.recippie.doctor.app.data.ReceiptData
import com.recippie.doctor.app.data.ReceiptRepository
import com.recippie.doctor.app.db.AppDataBase
import com.recippie.doctor.app.interfaces.BaseReceipt
import com.recippie.doctor.app.pojo.Receipt
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ReceiptViewModel(val app : Application) : AndroidViewModel(app), BaseReceipt {

    private val repository = ReceiptRepository(app)
    private val _recipeList: MutableLiveData<MutableList<Receipt>> = MutableLiveData()
    val recipeList = _recipeList


    override fun loadReceiptPage(forceReload: Boolean)= viewModelScope.launch {
        if (!forceReload) {
            return@launch
        }
        async { loadReceipt() }
    }

    override fun loadReceipt()= viewModelScope.launch {
        if (repository.existReceipt()) {
            val lastReceipt = repository.getLastReceipt()
            val listReceipt = repository.getCurrentReceipt(lastReceipt).map {
                Receipt(it.description, it.eachTime, it.duringTime)
            }
            _recipeList.postValue(listReceipt.toMutableList())
        }
    }

    override fun addReceipt(adapter: ReceiptAdapter)= viewModelScope.launch {
        adapter.addData(Receipt())
        repository.insertReceipt(ReceiptData(
            1,
            1,
            "Tabletas 5mg",
            "8",
            "6"))
    }

    override fun deleteReceipt()= viewModelScope.launch {

    }


}