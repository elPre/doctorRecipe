package com.recippie.doctor.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recippie.doctor.app.pojo.Receipt
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ReceiptViewModel : ViewModel() {

    private val _recipeList: MutableLiveData<List<Receipt>> = MutableLiveData()
    val recipeList = _recipeList

    fun loadRecipesItems(forceReload: Boolean = true) = viewModelScope.launch {
        if (!forceReload) {
            return@launch
        }
        async { loadRecipes() }
    }

    private fun loadRecipes() = viewModelScope.launch {
        var list: MutableList<Receipt> = mutableListOf()
        for (i in 1..20) {
            list.add(Receipt("description $i", "8 hrs", "8 dias"))
        }
        _recipeList.postValue(list.toList())
    }

}