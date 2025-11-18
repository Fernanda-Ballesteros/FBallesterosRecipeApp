package org.lasalle.recipeapp.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.lasalle.recipeapp.ui.viewmodels.AuthViewModel

@Composable
fun LoginScreen(){
    val colors = MaterialTheme.colorScheme
    val authViewModel : AuthViewModel = viewModel()
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)

    ){
        // Fondo
        Column(
            modifier = Modifier.fillMaxSize()
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(
                        bottomStart = 30.dp,
                        bottomEnd = 30.dp
                    ))
                    .background(colors.primary)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            )

        }

        // Card
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 20.dp)
                .height(350.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(colors.surface),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Text(
                text = "Bienvenido"
            )
            OutlinedTextField(
                value = email   ,
                onValueChange = { email = it},
                placeholder = { Text("Correo Electrónico") },
                modifier = Modifier
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it},
                placeholder = { Text("Contraseña", color = colors.onSurfaceVariant) },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = colors.primary,

                )

            )
            Button(onClick = {
                if(email.isBlank() || password.isBlank()) return@Button
                authViewModel.login(
                    email = email,
                    password = password
                )

            }, colors = ButtonDefaults.buttonColors() ){
                Text("Iniciar Sesión")
            }
        }
    }
}
