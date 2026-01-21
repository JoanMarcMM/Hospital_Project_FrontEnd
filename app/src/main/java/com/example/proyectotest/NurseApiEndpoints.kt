package com.example.proyectotest


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NurseApiEndpoints {

    // 1. La anotación @GET define el tipo de petición y la ruta relativa
    @GET("nurse/index")
    // 2. La función debe ser 'suspend' para que no bloquee la pantalla
    suspend fun getAllNurses(): List<Nurse>

    @POST("nurse/login")
    suspend fun login(@Body body: NurseViewModel.LoginRequest): Response<Boolean>

}