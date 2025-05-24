package com.example.foodappdacs.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodappdacs.api.RetrofitClient
import com.example.foodappdacs.model.Category
import kotlinx.coroutines.launch


class CategoryViewModel: ViewModel() {
    private val categoryApi= RetrofitClient.createApiCategory()
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>>
    get()= _categories

    fun getCategories(){
        viewModelScope.launch {
            _categories.value = categoryApi.getCategories()
        }
    }

    fun refreshCategories(){
        viewModelScope.launch {
            _categories.value = categoryApi.getCategories()
        }
    }
}