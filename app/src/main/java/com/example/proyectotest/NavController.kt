package com.example.proyectotest

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController



    @Composable
    fun AppNavigation(nurseViewModel: NurseViewModel) {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Routes.LOGIN // Inicia en la pantalla de Login
        ) {
            // --- 1. Pantalla de Login ---
            composable(Routes.LOGIN) {
                LogIn(
                    modifier = Modifier,
                    nurseViewModel = nurseViewModel,
                    onLoginSuccess = { navController.navigate(Routes.HOME) },
                    onRegisterClicked = { navController.navigate(Routes.REGISTER) }
                )
            }

            // --- 2. Pantalla de Registro ---
            composable(Routes.REGISTER) {
                RegisterScreen(
                    viewModel = nurseViewModel,
                    onRegistrationSuccess = {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) { inclusive = true }
                        }
                    }
                )
            }

            // --- 3. Pantalla de Lista de Enfermeras ---
            composable(Routes.SHOW_NURSES) {
                NurseListScreen(
                    onNavigateBack = {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) { inclusive = true }
                        }
                    }
                )
            }

            // --- 4. Pantalla de Búsqueda ---
            composable(Routes.SEARCH_BY_NAME) {
                SearchView(
                    viewModel = nurseViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // --- 5. Pantalla de Inicio ---
            composable(Routes.HOME) {
                HomeScreen(
                    onLoginClicked = { navController.navigate(Routes.LOGIN) },
                    onShowNursesClicked = { navController.navigate(Routes.SHOW_NURSES) },
                    onSearchClicked = { navController.navigate(Routes.SEARCH_BY_NAME) }
                )
            }
        }
    }
