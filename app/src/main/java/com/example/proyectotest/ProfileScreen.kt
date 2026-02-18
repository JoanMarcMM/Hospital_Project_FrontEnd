package com.example.proyectotest

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val HospitalBlue = Color(0xFF1E88E5)
val HospitalBlueLight = Color(0xFFE3F2FD)
val HospitalGreen = Color(0xFF43A047)

@Composable
fun ProfileScreen(
    viewModel: NurseViewModel,
    onLogout: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val nurse by viewModel.currentUser.observeAsState()

    ProfileContent(
        nurse = nurse,
        onNavigateBack = onNavigateBack,
        onUpdateProfile = { updatedNurse, onFinished ->
            viewModel.updateProfile(updatedNurse.id, updatedNurse) {
                onFinished()
            }
        },
        onDeleteAccount = { id ->
            viewModel.deleteAccount(id) {
                onLogout()
            }
        }
    )
}

@Composable
fun ProfileContent(
    nurse: Nurse?,
    onNavigateBack: () -> Unit,
    onUpdateProfile: (Nurse, () -> Unit) -> Unit,
    onDeleteAccount: (Long) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var editName by remember { mutableStateOf("") }
    var editLastname by remember { mutableStateOf("") }
    var editUser by remember { mutableStateOf("") }

    LaunchedEffect(nurse) {
        nurse?.let {
            editName = it.name
            editLastname = it.lastname
            editUser = it.user
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateBack,
                modifier = Modifier.padding(start = 16.dp),
                containerColor = HospitalBlue,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Volver al inicio"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Start
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (nurse != null) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "PERFIL DE USUARIO",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = HospitalBlue
                )
                Spacer(modifier = Modifier.height(20.dp))


                val imagenId = when(nurse.imageId) {
                    1 -> R.drawable.nurse_1
                    2 -> R.drawable.nurse_2
                    3 -> R.drawable.nurse_3
                    4 -> R.drawable.nurse_4
                    else -> R.drawable.profile_picture
                }

                Image(
                    painter = painterResource(id = imagenId),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (isEditing) {
                    OutlinedTextField(value = editName, onValueChange = { editName = it }, label = { Text("Nombre") })
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = editLastname, onValueChange = { editLastname = it }, label = { Text("Apellido") })
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = editUser, onValueChange = { editUser = it }, label = { Text("Usuario") })
                    Spacer(modifier = Modifier.height(16.dp))

                    Row {
                        Button(
                            onClick = {
                                val updatedNurse = nurse.copy(name = editName, lastname = editLastname, user = editUser)
                                onUpdateProfile(updatedNurse) { isEditing = false }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = HospitalGreen)
                        ) { Text("Guardar") }

                        Spacer(modifier = Modifier.width(16.dp))

                        Button(
                            onClick = { isEditing = false },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                        ) { Text("Cancelar") }
                    }

                } else {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        colors = CardDefaults.cardColors(containerColor = HospitalBlueLight)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("ID: ${nurse.id}", fontWeight = FontWeight.Bold, color = Color.Black)
                            Text("Nombre: ${nurse.name}", color = Color.Black)
                            Text("Apellido: ${nurse.lastname}", color = Color.Black)
                            Text("Usuario: ${nurse.user}", color = Color.Black)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { isEditing = true },
                        colors = ButtonDefaults.buttonColors(containerColor = HospitalGreen)
                    ) { Text("Editar mis datos") }
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { onDeleteAccount(nurse.id) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("DARSE DE BAJA")
                }

                Spacer(modifier = Modifier.height(80.dp))

            } else {
                CircularProgressIndicator(color = HospitalBlue)
                Text("Cargando perfil...")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    val fakeNurse = Nurse(
        id = 1,
        name = "Mario",
        lastname = "Pérez",
        user = "mario.perez",
        pw = "1234",
        imageId = 1
    )

    ProfileContent(
        nurse = fakeNurse,
        onNavigateBack = {},
        onUpdateProfile = { _, cb -> cb() },
        onDeleteAccount = {}
    )
}