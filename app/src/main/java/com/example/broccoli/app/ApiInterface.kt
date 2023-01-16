package com.example.broccoli.app

import com.example.broccoli.model.UserDetails
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {
    @Headers("Content-Type: application/json")
    @POST("https://us-central1-blinkapp-684c1.cloudfunctions.net/fakeAuth/")
    suspend fun postUserDetails(@Body userDetails: UserDetails) : Response<String>
}