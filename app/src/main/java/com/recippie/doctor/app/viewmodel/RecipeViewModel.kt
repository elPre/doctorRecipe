package com.recippie.doctor.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recippie.doctor.app.pojo.Recipe
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {

    private val _recipeList: MutableLiveData<List<Recipe>> = MutableLiveData()
    val recipeList = _recipeList

    fun loadRecipes() = viewModelScope.launch {
        var list: MutableList<Recipe> = mutableListOf()
        for (i in 1..20) {
            list.add(Recipe("description $1", "8 hrs", "8 dias"))
        }
        _recipeList.postValue(list.toList())
    }

}