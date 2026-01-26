package com.example.proyectotest


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NurseApiEndpoints {


    @GET("nurse/index")
    suspend fun getAllNurses(): List<Nurse>

    @POST("nurse/login")
    suspend fun login(@Body body: NurseViewModel.LoginRequest): Response<Nurse>

    @POST("nurse/new")
    suspend fun register(@Body body: NurseViewModel.RegisterRequest):Response<Nurse>

}