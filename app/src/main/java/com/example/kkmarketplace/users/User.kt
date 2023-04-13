package com.example.kkmarketplace.users

data class User(
    val id: Int,
    val firstname: String,
    val secondname: String,
    val email: String,
    val hashed_password: String,
    val status: String,
    val message: String
)
