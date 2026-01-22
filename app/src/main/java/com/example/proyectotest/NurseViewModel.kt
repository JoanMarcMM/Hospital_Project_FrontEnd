package com.example.proyectotest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.util.Log // Importante para el Log.d

class NurseViewModel: ViewModel() {

    // Iniciamos con la lista del DataHolder
    private val _nurseList = MutableLiveData<List<Nurse>>()
    val nurseList: LiveData<List<Nurse>> = _nurseList

    fun addNurse(nurse: Nurse) {
        val currentList = _nurseList.value.orEmpty().toMutableList()
        currentList.add(nurse)
        _nurseList.value = currentList
    }


    fun fetchNursesFromApi() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getAllNurses()
                _nurseList.postValue(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error: ${e.message}")
                _nurseList.postValue(emptyList())
            }
        }
    }
    fun fetchNursesByName(query: String) {
        viewModelScope.launch {
            try {
                val response = if (query.isEmpty()) {
                    RetrofitClient.instance.getAllNurses()
                } else {
                    RetrofitClient.instance.searchNursesByName(query)
                }
                _nurseList.postValue(response)
            } catch (e: Exception) {
                _nurseList.postValue(emptyList())
            }
        }
    }



    fun registerNewNurse(name: String, lastname: String, user: String, pw: String) {
        val newNurse = Nurse(
            id = System.currentTimeMillis(),
            name = name,
            lastname = lastname,
            user = user,
            pw = pw,
            imageId = R.drawable.nurse_generico
        )

        val currentList = _nurseList.value.orEmpty().toMutableList()
        currentList.add(newNurse)
        _nurseList.value = currentList
    }

    data class LoginRequest(
        val user: String,
        val pw: String
    )

    suspend fun login(user: String, pw: String): Boolean {
        return try {
            val res = RetrofitClient.instance.login(LoginRequest(user, pw))
            android.util.Log.d("LOGIN", "code=${res.code()} body=${res.body()} error=${res.errorBody()?.string()}")
            res.isSuccessful && (res.body() == true)
        } catch (e: Exception) {
            android.util.Log.d("LOGIN", "EXCEPTION: ${e.message}", e)
            false
        }
    }


    data class RegisterRequest(
        val user: String,
        val pw: String,
        val name: String,
        val lastname: String
    )

    suspend fun register(user: String, pw: String, name: String, lastname: String): Boolean {
        return try {
            val res = RetrofitClient.instance.register(
                RegisterRequest(user, pw, name, lastname)
            )

            Log.d(
                "REGISTER",
                "code=${res.code()} isSuccessful=${res.isSuccessful} body=${res.body()} error=${res.errorBody()?.string()}"
            )

            // ✅ 201 + Nurse en body => éxito
            res.isSuccessful && (res.body() != null)

        } catch (e: Exception) {
            Log.d("REGISTER", "EXCEPTION: ${e.message}", e)
            false
        }
    }


}
