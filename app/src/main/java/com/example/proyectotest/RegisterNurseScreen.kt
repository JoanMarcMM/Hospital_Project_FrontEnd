package com.example.proyectotest
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterScreen(viewModel: NurseViewModel) {
    // Variables de estado para los campos de texto
    var name by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 1. IMAGEN DE FONDO (Estetoscopio)
        // Asegúrate de tener una imagen de fondo en tus drawables, ej: 'bg_medical'
        Image(
            painter = painterResource(id = R.drawable.loginbackground), // CAMBIA ESTO por tu imagen real
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Capa oscura semitransparente para que se lea el texto
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )

        // 2. FORMULARIO CENTRADO
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Título
            Box(modifier = Modifier.background(Color.Black).padding(12.dp)) {
                Text(
                    text = "NURSE REGISTRATION",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Campos de texto (Reutilizamos un estilo común)
            RegisterTextField(value = name, onValueChange = { name = it }, label = "Name")
            Spacer(modifier = Modifier.height(12.dp))

            RegisterTextField(value = lastname, onValueChange = { lastname = it }, label = "Lastname")
            Spacer(modifier = Modifier.height(12.dp))

            RegisterTextField(value = username, onValueChange = { username = it }, label = "Username")
            Spacer(modifier = Modifier.height(12.dp))

            // Campo de contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White.copy(alpha = 0.9f),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // BOTÓN REGISTRAR
            Button(
                onClick = {
                    if (name.isNotEmpty() && username.isNotEmpty()) {
                        viewModel.registerNewNurse(name, lastname, username, password)
                        val intent = Intent(context, ShowNurses::class.java)
                        context.startActivity(intent)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "REGISTER", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}
@Composable
fun RegisterTextField(value: String, onValueChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White.copy(alpha = 0.9f),
            unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    )
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    val viewModelDePrueba = NurseViewModel()
    RegisterScreen(viewModel = viewModelDePrueba)
}
