package com.recippie.doctor.app.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.recippie.doctor.app.adapter.ReceiptAdapter
import com.recippie.doctor.app.db.AppDataBase
import com.recippie.doctor.app.interfaces.BaseReceipt
import com.recippie.doctor.app.pojo.Receipt
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ReceiptViewModel(val app: Application) : AndroidViewModel(app), BaseReceipt {

    private val _recipeList: MutableLiveData<MutableList<Receipt>> = MutableLiveData()
    val recipeList = _recipeList

//    val db = Room.databaseBuilder(
//        app,
//        AppDataBase::class.java, "database-name"
//    ).build()

    override fun loadReceiptPage(forceReload: Boolean)= viewModelScope.launch {
        if (!forceReload) {
            return@launch
        }
        async { loadReceipt() }
    }

    override fun loadReceipt()= viewModelScope.launch {

    }

    override fun addReceipt(adapter: ReceiptAdapter)= viewModelScope.launch {
        adapter.addData(Receipt())
    }

    override fun deleteReceipt()= viewModelScope.launch {

    }
}