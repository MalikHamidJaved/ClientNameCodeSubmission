package com.apex.codeassesment.api

import com.apex.codeassesment.data.model.User
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("api")
    fun getUser(): Call<GetUsersResponse>

    @GET("api?results=10")
    fun getUsers(): Call<GetUsersResponse>
}
