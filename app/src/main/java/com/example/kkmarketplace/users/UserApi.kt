package com.example.kkmarketplace.users

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface UserApi {
    @GET("user/{id}")
    suspend fun getUserById(@Path("id") id: Int): User

    @GET("user/email/{email}")
    suspend fun getUserByEmailAndPassword(@Path("email") email: String): User

    @POST("users")
    suspend fun postUser(@Body user: User)
}