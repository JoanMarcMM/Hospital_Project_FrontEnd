package com.example.proyectotest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.util.Log // Importante para el Log.d


object NurseDataHolder {

     val initialNurses = listOf(
        Nurse(1, "Mario", "Hermano", "mariobros", "1234", R.drawable.mario),
        Nurse(2, "Marvin", "Marciano", "marvin_space", "5678", R.drawable.marvin),
        Nurse(3, "GianMarc", "Motis", "gmotis", "abcd", R.drawable.motis),
        Nurse(4, "Rodrigo", "Sopero", "rodri_caldo", "xyz", R.drawable.rodrigo)
    )




}


class NurseViewModel: ViewModel() {

    // Iniciamos con la lista del DataHolder
    private val _nurseList = MutableLiveData<List<Nurse>>(NurseDataHolder.initialNurses)
    val nurseList: LiveData<List<Nurse>> = _nurseList

    fun addNurse(nurse: Nurse) {
        val currentList = _nurseList.value.orEmpty().toMutableList()
        currentList.add(nurse)
        _nurseList.value = currentList
    }

    // 1. Obtener enfermeros de la API [cite: 30]
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

    // 2. Registro local (añade a la lista actual)
    fun registerNewNurse(name: String, lastname: String, user: String, pw: String) {
        val newNurse = Nurse(
            id = System.currentTimeMillis(),
            name = name,
            lastname = lastname,
            user = user,
            pw = pw,
            imageId = R.drawable.nurse_generico
        )

        // Actualizamos la lista del LiveData para que la UI se refresque
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
            RetrofitClient.instance.login(LoginRequest(user, pw))
        } catch (e: Exception) {
            false
        }
    }
    suspend fun logInNurse(user: String, pw: String): Boolean {
        return try {
            val response = RetrofitClient.instance.getAllNurses()
            response.any { it.user == user && it.pw == pw }
        } catch (e: Exception) {
            false
        }
    }

}
