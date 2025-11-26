package com.example.proyectotest

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.proyectotest.ui.theme.ProyectoTestTheme
import androidx.compose.ui.res.stringResource


class LogIn : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LogIn()

                }
            }
        }
    }


@SuppressLint("NotConstructor")
@Composable
fun LogIn(modifier: Modifier = Modifier) {
    var user by remember { mutableStateOf("")}
    var pw by remember { mutableStateOf("")}
    var loggedIn by remember { mutableStateOf(false)}
    var logInError by remember {mutableStateOf(false)}

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.loginbackground), // tu imagen en res/drawable
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        if(loggedIn==true){
            LoggedIn(user,pw)
        }else{
            Column(
                modifier = Modifier.padding(horizontal = 20.dp )
                    .padding(vertical = 60.dp),
                horizontalAlignment = Alignment.CenterHorizontally,


            ) {
                Text(text =stringResource(id = R.string.log_in_form_title), modifier = Modifier.background(color = colorResource(id = R.color.black)).padding(10.dp), color = colorResource(id = R.color.white))
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = user,
                    onValueChange = { user = it },
                    label = { Text(text=stringResource(id = R.string.log_in_username_field)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = pw,
                    onValueChange = { pw = it },
                    label = { Text(text=stringResource(id = R.string.log_in_password_field)) },
                    modifier = Modifier.fillMaxWidth()
                )
                if(logInError==true){
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text =stringResource(id = R.string.log_in_error), color = colorResource(id = R.color.red))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (CheckUser(user, pw)) {
                            loggedIn=true
                            logInError=false
                        } else {
                            logInError=true
                            loggedIn=false
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.black),
                        contentColor   = colorResource(id = R.color.white)
                    )
                ) {
                    Text(text=stringResource(id = R.string.log_in_button))
                }


            }
        }

    }





}

fun  CheckUser(user:String,pw:String):Boolean{

    if(user=="nurse1"&&pw=="pw1234"){
        return true
    }else{
        return false
    }

}



@Composable
fun LoggedIn(user:String,pw:String) {
    var loggedOut by remember { mutableStateOf(false) }
    val name = "Pepita"
    val lastname = "Grilla"
    val id = "00142"

    if (loggedOut == true) {
        LogIn()
    } else {

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = stringResource(id = R.string.logged_in_greeting, name),
                modifier = Modifier.background(color = colorResource(id = R.color.black)),
                color = colorResource(id = R.color.white)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Image(
                painter = painterResource(id = R.drawable.profile_picture), // tu imagen en res/drawable
                contentDescription = null,
                modifier = Modifier
                    .size(290.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,


                )
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier.background(color = colorResource(id = R.color.black))
                    .padding(10.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(id = R.string.logged_in_id, id),
                    color = colorResource(id = R.color.white)
                )
                Text(
                    text = stringResource(id = R.string.logged_in_name, name),
                    color = colorResource(id = R.color.white)
                )
                Text(
                    text = stringResource(id = R.string.logged_in_lastname, lastname),
                    color = colorResource(id = R.color.white)
                )
                Text(
                    text = stringResource(id = R.string.logged_in_username, user),
                    color = colorResource(id = R.color.white)
                )
                Text(
                    text = stringResource(id = R.string.logged_in_password, pw),
                    color = colorResource(id = R.color.white)
                )

            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    loggedOut = true
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.black),
                    contentColor = colorResource(id = R.color.white)
                )
            ) {
                Text(text = stringResource(id = R.string.log_out_button, id))
            }
        }

    }
}
    }











