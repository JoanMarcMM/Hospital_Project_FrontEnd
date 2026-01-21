package com.example.proyectotest

sealed interface NurseListUiState {
    data class Success(val enfermeros: List<Nurse>) : NurseListUiState
    object Error : NurseListUiState
    object Cargando : NurseListUiState
}