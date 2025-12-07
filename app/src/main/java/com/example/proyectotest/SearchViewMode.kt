package com.example.proyectotest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// Objeto Singleton para que la lista sea la misma en toda la app.
object NurseDataHolder {
    private val initialNurses = listOf(
        Nurses(1, "Mario", "Hermano", "mariobros", "1234", R.drawable.mario),
        Nurses(2, "Marvin", "Marciano", "marvin_space", "5678", R.drawable.marvin),
        Nurses(3, "GianMarc", "Motis", "gmotis", "abcd", R.drawable.motis),
        Nurses(4, "Rodrigo", "Sopero", "rodri_caldo", "xyz", R.drawable.rodrigo)
    )

    private val _nurseList = MutableLiveData<List<Nurses>>(initialNurses)
    val nurseList: LiveData<List<Nurses>> = _nurseList

    fun addNurse(nurse: Nurses) {
        val currentList = _nurseList.value.orEmpty().toMutableList()
        currentList.add(nurse)
        _nurseList.value = currentList
    }
}

class SearchViewMode: ViewModel() {

    // El ViewModel ahora usa el Singleton como fuente de datos.
    val nurseList: LiveData<List<Nurses>> = NurseDataHolder.nurseList

    fun registerNewNurse(name: String, lastname: String, user: String, pw: String) {
        val newNurse = Nurses(
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
}

