package com.example.foodappdacs.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodappdacs.api.RetrofitClient
import com.example.foodappdacs.model.Food
import kotlinx.coroutines.launch

class FoodViewModel: ViewModel() {
    private val foodApi = RetrofitClient.createApiFood()
    private val _foods = MutableLiveData<List<Food>>()
    private val _food = MutableLiveData<Food>()
    val foods : LiveData<List<Food>>
        get() = _foods
    val food : LiveData<Food> = _food

    fun getFoods(){
        viewModelScope.launch {
            _foods.value = foodApi.getFoods()
        }
    }

    fun getFoodById(id:Int){
        viewModelScope.launch {
           _food.value = foodApi.getFoodById(id)
        }
    }
}