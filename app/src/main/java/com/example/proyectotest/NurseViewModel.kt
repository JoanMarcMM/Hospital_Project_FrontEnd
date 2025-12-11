package com.example.proyectotest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// Objeto Singleton para que la lista sea la misma en toda la app.
object NurseDataHolder {
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
}

class NurseViewModel: ViewModel() {

    // El ViewModel ahora usa el Singleton como fuente de datos.
    val nurseList: LiveData<List<Nurse>> = NurseDataHolder.nurseList

    fun registerNewNurse(name: String, lastname: String, user: String, pw: String) {
        val newNurse = Nurse(
            id = System.currentTimeMillis(), // Generamos ID automático
            name = name,
            lastname = lastname,
            user = user,
            pw = pw,
            imageId = R.drawable.nurse_generico // Imagen por defecto
        )
        // La lógica de añadir se delega al Singleton.
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

