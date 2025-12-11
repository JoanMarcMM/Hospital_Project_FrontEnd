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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

class ShowNurses : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NurseListScreen()
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
 */
@Composable
fun NurseListHeader(onBackClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onBackClicked) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = stringResource(id = R.string.homepage),
                tint = Color.Companion.Black
            )
        }
        Text(
            text = "List of Nurses",
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            style = LocalTextStyle.current.copy(
                brush = GreenBlueGradient
            )
        )
    }
}

/**
 * Composable para la tarjeta individual de un enfermero.
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
 */
@Composable
fun NurseListScreen()  {
    val context = LocalContext.current
    // Obtenemos la lista real desde nuestro objeto central (NurseDataHolder)
    val nurses by NurseDataHolder.nurseList.observeAsState(initial = emptyList())


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBackgroundColor),
        topBar = {
            Surface(color = Color.Companion.Transparent) {
                NurseListHeader(onBackClicked = {
                    val intent = Intent(context, Homepage::class.java)
                    context.startActivity(intent)
                })
            }
        }
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
                    NurseCard(nurse = nurse) //
                }
                item {
                    Spacer(modifier = Modifier.height(32.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_hospital),
                            contentDescription = "Logo Hospital y Wellness Center",
                            modifier = Modifier.size(325.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }

    }
}
