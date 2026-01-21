package com.example.proyectotest


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    val instance: NurseApiEndpoints by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NurseApiEndpoints::class.java)

    }

}
