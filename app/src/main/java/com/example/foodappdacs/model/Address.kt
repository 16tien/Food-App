package com.example.foodappdacs.model

data class Address(
    val address_id: Int,
    val user_id: Int,
    val street : String,
    val commune: String,
    val district: String,
    val phone_address: Int
) {
    fun getAddress():String{
        return "${this.street}, ${this.commune}, ${this.district}"
    }
}