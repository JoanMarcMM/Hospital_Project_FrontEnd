package com.example.proyectotest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
class SearchViewMode: ViewModel() {
    private var _nurseList = MutableLiveData<List<Nurses>>()
    val nurseList: LiveData<List<Nurses>> get() = _nurseList

    init {
        _nurseList.value = dataNurses()
    }
}

fun dataNurses(): List<Nurses>{
    return listOf(
        Nurses("Mario", R.drawable.mario),
        Nurses("Marvin", R.drawable.marvin),
        Nurses("Motis", R.drawable.motis),
        Nurses("Rodrigo", R.drawable.rodrigo)
    )

}