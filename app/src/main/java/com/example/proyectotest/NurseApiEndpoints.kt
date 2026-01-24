package com.example.proyectotest


import okhttp3.ResponseBody
import retrofit2.http.*
import retrofit2.http.Query
import retrofit2.Response
interface NurseApiEndpoints {

    @GET("nurse/index")
    suspend fun getAllNurses(): List<Nurse>

    @POST("nurse/login")
    suspend fun login(@Body body: NurseViewModel.LoginRequest): Response<Boolean>

    @POST("nurse/new")
    suspend fun register(@Body body: NurseViewModel.RegisterRequest):Response<Nurse>

    @GET("nurse/name/{name}")
    suspend fun searchNursesByName(@Path("name") name: String): List<Nurse>

    @GET("nurse/{id}")
    suspend fun getNurseById(@Path("id") id: Long): Response<Nurse>

    @PUT("nurse/{id}")
    suspend fun updateNurse(
        @Path("id") id: Long,
        @Body nurse: Nurse
    ): Response<Nurse>

    @DELETE("nurse/{id}")
    suspend fun deleteNurse(@Path("id") id: Long): Response<ResponseBody>
}