package com.example.proyectotest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
interface ApiService {
        @GET("/nurse/index")
        suspend fun getNurses(
        ): Response<List<Nurse>>
}