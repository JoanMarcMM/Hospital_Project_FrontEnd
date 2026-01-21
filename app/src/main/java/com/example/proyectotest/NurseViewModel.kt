package com.example.proyectotest


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

object NurseDataHolder {
    /*
    private val initialNurses = listOf(
        Nurse(1, "Mario", "Hermano", "mariobros", "1234", R.drawable.mario),
        Nurse(2, "Marvin", "Marciano", "marvin_space", "5678", R.drawable.marvin),
        Nurse(3, "GianMarc", "Motis", "gmotis", "abcd", R.drawable.motis),
        Nurse(4, "Rodrigo", "Sopero", "rodri_caldo", "xyz", R.drawable.rodrigo)
    )

    private val _nurseList = MutableLiveData<List<Nurse>>(initialNurses)
    val nurseList: LiveData<List<Nurse>> = _nurseList

    fun addNurse(nurse: Nurse) {
        val currentList = _nurseList.value.orEmpty().toMutableList()
        currentList.add(nurse)
        _nurseList.value = currentList
    }
    */
}

class NurseViewModel: ViewModel() {

    class NurseViewModel : ViewModel() {
        var nurseList by mutableStateOf<List<Nurse>>(emptyList())
        var isLoading by mutableStateOf(false)

        fun fetchNurses() {
            viewModelScope.launch {
                isLoading = true
                try {
                    val response = Retrofit.apiService.getNurses()
                    if (response.isSuccessful) {
                        nurseList = response.body() ?: emptyList()
                    } else {
                        // Manejar error de servidor
                    }
                } catch (e: Exception) {
                    // Manejar error de conexión (ej. falta de internet)
                    e.printStackTrace()
                } finally {
                    isLoading = false
                }
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

        NurseDataHolder.addNurse(newNurse)
    }

    fun logInNurse(user: String, pw: String): Boolean {
        val list = NurseDataHolder.nurseList.value ?: emptyList()

        for (nurse in list) {
            if (nurse.user == user && nurse.pw == pw) {
                return true
            }
        }
        return false
    }
}

