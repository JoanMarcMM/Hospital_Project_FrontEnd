package com.example.proyectotest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class RemoteViewModel : ViewModel() {

    object Connexio {
        private val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/nurse/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api: RemoteMessageInterface =
            retrofit.create(RemoteMessageInterface::class.java)
    }

    data class LoginRequest(
        val user: String,
        val pw: String
    )

    interface RemoteMessageInterface {
        @POST("login")
        suspend fun login(@Body body: LoginRequest): Boolean
    }


    suspend fun login(user: String, pw: String): Boolean {
        return try {
            Connexio.api.login(LoginRequest(user, pw))
        } catch (e: Exception) {
            false
        }
    }
}


