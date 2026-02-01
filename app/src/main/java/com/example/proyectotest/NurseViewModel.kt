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
    val currentUser = MutableLiveData<Nurse?>()

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
            imageId = 1
        )

        val currentList = _nurseList.value.orEmpty().toMutableList()
        currentList.add(newNurse)
        _nurseList.value = currentList
    }
    /*
    fun setUserAfterLogin(username: String) {
        val foundNurse = _nurseList.value?.find { it.user == username }

        if (foundNurse != null) {
            currentUser.postValue(foundNurse)
            Log.d("Login", "Usuario establecido: ${foundNurse.name}")
        } else {
            Log.e("Login", "¡Cuidado! Login correcto pero la lista de enfermeros estaba vacía.")
        }
    }
º*/
    fun updateProfile(id: Long, updatedNurse: Nurse, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                // 1. Enviamos los datos al servidor
                val response = RetrofitClient.instance.updateNurse(id, updatedNurse)

                if (response.isSuccessful) {



                    val nurseToUpdate = response.body() ?: updatedNurse

                    currentUser.postValue(nurseToUpdate)
                    val currentList = _nurseList.value.orEmpty().toMutableList()
                    val index = currentList.indexOfFirst { it.id == id }
                    if (index != -1) {
                        currentList[index] = nurseToUpdate
                        _nurseList.postValue(currentList)
                    }

                    onSuccess()
                } else {
                    val errorReal = response.errorBody()?.string()
                    Log.e("Update", "El servidor rechazó el cambio: ${response.code()}")
                    Log.e("ERROR_DETALLE", "El servidor dice: $errorReal")
                }
            } catch (e: Exception) {
                Log.e("Update", "Error de conexión: ${e.message}")
            }
        }
    }

    fun deleteAccount(id: Long, onDeleted: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.deleteNurse(id)


                if (response.isSuccessful) {
                    currentUser.postValue(null)
                    val currentList = _nurseList.value.orEmpty().toMutableList()
                    currentList.removeAll { it.id == id }
                    _nurseList.postValue(currentList)
                    onDeleted()
                } else {
                    Log.e("Delete", "Error al borrar: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("Delete", "Error de conexión al borrar")
            }
        }
    }
    data class LoginRequest(
        val user: String,
        val pw: String
    )

    suspend fun login(user: String, pw: String): Boolean {
        return try {
            val res = RetrofitClient.instance.login(LoginRequest(user, pw))
            res.isSuccessful && (res.body() == true)
        } catch (e: Exception) {
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

            res.isSuccessful && (res.body() != null)

        } catch (e: Exception) {
            false
        }
    }
    suspend fun fetchCurrentUserProfile(username: String, onResult: (Boolean) -> Unit) {
        try {
             val allNurses = RetrofitClient.instance.getAllNurses()


            val foundNurse = allNurses.find { it.user == username }

            if (foundNurse != null) {

                val nurseConImagen = foundNurse.copy(imageId = foundNurse.id.toInt())

                currentUser.postValue(nurseConImagen)
                onResult(true)
            } else {
                onResult(false)
            }
        } catch (e: Exception) {
            onResult(false)
        }
    }
}
