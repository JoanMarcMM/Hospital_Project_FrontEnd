package com.example.proyectotest


import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface NurseApiEndpoints {

    @GET("nurse/index")
    suspend fun getAllNurses(): List<Nurse>

    @POST("login")
    suspend fun login(@Body body: NurseViewModel.LoginRequest): Boolean

    @GET("nurse/name/{name}")
    suspend fun searchNursesByName(@Path("name") name: String): List<Nurse>

}