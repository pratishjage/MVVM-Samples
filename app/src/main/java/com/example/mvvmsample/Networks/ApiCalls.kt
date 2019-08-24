package com.example.mvvmsample.Networks

import com.example.mvvmsample.db.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiCalls {
    @GET("users")
    suspend fun getUsers(): Response<List<User>>
}
