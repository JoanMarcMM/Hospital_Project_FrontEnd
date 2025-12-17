package com.example.proyectotest

import androidx.lifecycle.viewmodel.compose.viewModel
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyectotest.ui.theme.ProyectoTestTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectotest.RegisterScreen
import com.example.proyectotest.NurseViewModel
import com.example.proyectotest.LogIn // de LogInNurseScreen.kt
import com.example.proyectotest.NurseListScreen // de ShowNurses.kt
import com.example.proyectotest.SearchView // de SearchNurses.kt

class Homepage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                ) {
                    val nurseViewModel: NurseViewModel = viewModel()
                    AppNavigation(nurseViewModel)
                    /*var modifier = Modifier;
                    //LogInNurseScreen.LogIn(modifier,nurseViewModel);
                    var login = LogInNurseScreen();
                    login.LogIn(
                        modifier = modifier,
                        nurseViewModel = nurseViewModel
                    )
                    */

                }
            }
        }
    }
}




@Composable
fun HomeScreen(onLoginClicked: () -> Unit,
               onShowNursesClicked: () -> Unit,
               onSearchClicked: () -> Unit) {
    Scaffold(
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = stringResource(R.string.welcome_to_the_hospital),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 25.sp
                    ),
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.logo_hospital),
                    contentDescription = stringResource(R.string.logo_del_hospital),
                    modifier = Modifier
                        .size(350.dp)
                        .padding(bottom = 48.dp)
                )


                AppButton(
                    text = stringResource(R.string.display_all_nurses),
                    onClick = { onShowNursesClicked() }
                )

                Spacer(modifier = Modifier.height(16.dp))

                AppButton(
                    text = stringResource(R.string.search_nurse_by_name),
                    onClick = { onSearchClicked() }
                )
            }


            Button(
                onClick = { onLoginClicked() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp),
                shape = RoundedCornerShape(12.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color.Red.copy(alpha = 0.8f)
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Log Out")
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_lock_power_off),
                        contentDescription = "Logout"
                    )
                }
            }
        }
    }
}

@Composable
fun AppButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = text, modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ProyectoTestTheme {
        HomeScreen(
            onLoginClicked = { /* No hace nada en Preview */ },
            onShowNursesClicked = { /* No hace nada en Preview */ },
            onSearchClicked = { /* No hace nada en Preview */ })
    }


}