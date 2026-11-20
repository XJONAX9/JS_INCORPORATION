package com.example.js_incorporation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.js_incorporation.viewmodels.UsuarioViewModel
import androidx.compose.ui.text.style.TextAlign

private val AzulOscuro = Color(0xFF101A30)
private val AzulTarjeta = Color(0xFF1D2A42)
private val Blanco = Color(0xFFF4F6FA)

@Composable
fun LoginScreen(
    usuarioViewModel: UsuarioViewModel,
    onLoginClick: () -> Unit,
    onRegistrarseClick: () -> Unit
) {

    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var errorLogin by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AzulOscuro)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = AzulTarjeta
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Iniciar Sesión",
                    color = Blanco,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = { Text("Correo") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Blanco,
                        unfocusedTextColor = Blanco,
                        focusedLabelColor = Blanco,
                        unfocusedLabelColor = Blanco
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = contrasena,
                    onValueChange = { contrasena = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Blanco,
                        unfocusedTextColor = Blanco,
                        focusedLabelColor = Blanco,
                        unfocusedLabelColor = Blanco
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        if (correo.isBlank()) {
                            errorLogin = "Debe ingresar un correo"
                        } else if (contrasena.isBlank()) {
                            errorLogin = "Debe ingresar una contraseña"
                        } else if (correo != usuarioViewModel.estado.value.correo) {
                            errorLogin = "El usuario no existe"
                        } else if (contrasena != usuarioViewModel.estado.value.clave) {
                            errorLogin = "La contraseña es incorrecta"
                        } else {
                            errorLogin = ""
                            onLoginClick()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ingresar")
                }

                if (errorLogin.isNotEmpty()) {
                    Text(
                        text = errorLogin,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                TextButton(
                    onClick = {
                        onRegistrarseClick()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "¿No tienes cuenta? Registrarse",
                        color = Blanco
                    )
                }
            }
        }
    }
}