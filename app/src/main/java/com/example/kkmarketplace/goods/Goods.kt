package com.example.kkmarketplace.goods

data class Goods(
    val id: Int,
    val title: String,
    val category: String,
    val price: String,
    val salesman_id: Int,
    val short_description: String,
    val long_description: String,
    val social_salesman: String,
    val phonenumber_salesman: String,
    val message: String
)