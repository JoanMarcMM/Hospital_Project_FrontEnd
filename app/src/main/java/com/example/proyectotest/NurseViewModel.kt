package com.example.proyectotest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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


    val nurseList: LiveData<List<Nurse>> = NurseDataHolder.nurseList


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

    suspend fun logInNurse(user: String, pw: String): Boolean {
        val vm = RemoteViewModel()
        return vm.login(user, pw)
    }
}

