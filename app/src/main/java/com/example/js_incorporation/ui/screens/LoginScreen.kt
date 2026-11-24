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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.js_incorporation.ui.utils.validarEmail
import com.example.js_incorporation.ui.utils.validarPassword
import com.example.js_incorporation.viewmodels.LoginViewModel
import com.example.js_incorporation.viewmodels.MainViewModel
import com.example.js_incorporation.viewmodels.ResultadoLogin

private val AzulOscuro = Color(0xFF101A30)
private val AzulTarjeta = Color(0xFF1D2A42)
private val Blanco = Color(0xFFF4F6FA)

@Composable
fun LoginScreen(
    viewModel: MainViewModel,
    onLoginClick: () -> Unit,
    onRegistrarseClick: () -> Unit,
    loginViewModel: LoginViewModel = viewModel()
) {

    var correo by rememberSaveable { mutableStateOf("") }
    var contrasena by rememberSaveable { mutableStateOf("") }
    var intentoEnviar by rememberSaveable { mutableStateOf(false) }

    val isLoading by loginViewModel.isLoading.collectAsState()
    val resultado by loginViewModel.resultado.collectAsState()

    val errorFormato = if (intentoEnviar) validarEmail(correo) ?: validarPassword(contrasena) else null
    val errorServidor = (resultado as? ResultadoLogin.Error)?.mensaje
    val errorLogin = errorFormato ?: errorServidor ?: ""

    LaunchedEffect(resultado) {
        val r = resultado
        if (r is ResultadoLogin.Exito) {
            viewModel.iniciarSesion(r.usuario)
            loginViewModel.limpiarResultado()
            onLoginClick()
        }
    }

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
                    onValueChange = { correo = it.trim() },
                    label = { Text("Correo") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
                        intentoEnviar = true
                        val formatoValido =
                            validarEmail(correo) == null && validarPassword(contrasena) == null
                        if (formatoValido) {
                            loginViewModel.iniciarSesion(correo, contrasena)
                        }
                    },
                    enabled = !isLoading,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Ingresar")
                    }
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