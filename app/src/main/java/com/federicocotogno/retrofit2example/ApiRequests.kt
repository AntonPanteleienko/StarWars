package com.federicocotogno.retrofit2example

import com.federicocotogno.retrofit2example.api.Wars
import retrofit2.Call
import retrofit2.http.GET


interface ApiRequests {



    @GET("/api/people/10/")
    fun getPerson(): Call<Wars>
}