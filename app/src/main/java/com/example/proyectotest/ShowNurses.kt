package com.example.proyectotest

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign


// NOTA IMPORTANTE: Para que esto compile, debes asegurarte de que Nurse, NurseDataHolder y
// R.drawable.logo_hospital (o cualquier recurso usado) existan en tu proyecto.

class ShowNurses : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // NOTA: Si no estás usando un NavController en tu actividad principal,
            // puedes cambiar la implementación de onNavigateBack
            NurseListScreen(onNavigateBack = {
                // Opción 1: Si quieres usar el Intent directo como en el código base original:
                val intent = Intent(this, Homepage::class.java)
                startActivity(intent)

                // Opción 2: Si usas NavController (reemplaza la Opción 1 si es tu caso):
                // navController.popBackStack() // O la lógica de navegación que uses para ir a Home
            })
        }
    }
}


val GreenBlueGradient = Brush.Companion.linearGradient(
    colors = listOf(Color(0xFF4CAF50), Color(0xFF2196F3)), // Verde y Azul
    start = Offset(0f, 0f),
    end = Offset(1000f, 1000f)
)

val ScreenBackgroundColor = Color(0xFFE0F7FA)


/**
 * Composable para la cabecera (Header).
 * ADAPTADO: Título centrado y de color negro sólido.
 */
@Composable
fun NurseListHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center // Centramos el contenido
    ) {
        Text(
            text = "List of Nurses",
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black, // Color de texto negro sólido
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
    Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Color.LightGray)
}

/**
 * Composable para la tarjeta individual de un enfermero (Sin cambios).
 */
@Composable
fun NurseCard(nurse: Nurse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (nurse.imageId != 0) {
                Image(
                    painter = painterResource(id = nurse.imageId),
                    contentDescription = "Foto de ${nurse.name}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFC8E6C9)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = nurse.name.firstOrNull()?.toString()?.uppercase() ?: "?",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF388E3C)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = nurse.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Text(
                    text = "Enfermero/a Registrado",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

/**
 * Composable de la pantalla principal que combina la cabecera y la lista.
 * ADAPTADO: Usa FloatingActionButton para la navegación y elimina la imagen del logo.
 */
@Composable
fun NurseListScreen(onNavigateBack: () -> Unit)  {
    val nurses by NurseDataHolder.nurseList.observeAsState(initial = emptyList())

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBackgroundColor),
        topBar = {
            Surface(color = Color.Companion.Transparent) {
                NurseListHeader()
            }
        },
        // Botón de Home (FAB)
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateBack, // Usa la función pasada para navegar
                modifier = Modifier.padding(start = 16.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(R.string.homepage)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Start
    ){ paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(nurses) { nurse ->
                    NurseCard(nurse = nurse)
                }
                item {
                    Spacer(modifier = Modifier.height(32.dp))
                    // La imagen del logo que estaba aquí ha sido eliminada.
                }
            }
        }
    }
}