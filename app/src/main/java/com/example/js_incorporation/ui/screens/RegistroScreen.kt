package com.example.js_incorporation.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.js_incorporation.model.Rol
import com.example.js_incorporation.ui.utils.validarConfirmacion
import com.example.js_incorporation.ui.utils.validarEmail
import com.example.js_incorporation.ui.utils.validarNombre
import com.example.js_incorporation.ui.utils.validarPassword
import com.example.js_incorporation.ui.utils.validarRut
import com.example.js_incorporation.viewmodels.RegistroViewModel
import com.example.js_incorporation.viewmodels.ResultadoRegistro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    navController: NavController,
    registroViewModel: RegistroViewModel = viewModel()
) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var rut by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    var clave by rememberSaveable { mutableStateOf("") }
    var confirmacion by rememberSaveable { mutableStateOf("") }
    var rolSeleccionado by rememberSaveable { mutableStateOf(Rol.BODEGA.name) }
    var aceptaTerminos by rememberSaveable { mutableStateOf(false) }
    var intentoEnviar by rememberSaveable { mutableStateOf(false) }

    val isLoading by registroViewModel.isLoading.collectAsState()
    val resultado by registroViewModel.resultado.collectAsState()
    val context = LocalContext.current

    val errorNombre = if (intentoEnviar) validarNombre(nombre) else null
    val errorRut = if (intentoEnviar) validarRut(rut) else null
    val errorCorreo = if (intentoEnviar) validarEmail(correo) else null
    val errorClave = if (intentoEnviar) validarPassword(clave) else null
    val errorConfirmacion = if (intentoEnviar) validarConfirmacion(clave, confirmacion) else null

    LaunchedEffect(resultado) {
        if (resultado is ResultadoRegistro.Exito) {
            Toast.makeText(
                context,
                "Bienvenido $nombre, registro exitoso",
                Toast.LENGTH_SHORT
            ).show()
            registroViewModel.limpiarResultado()
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de usuario") }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre completo") },
                singleLine = true,
                isError = errorNombre != null,
                supportingText = { errorNombre?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = rut,
                onValueChange = { rut = it.trim() },
                label = { Text("RUT") },
                placeholder = { Text("12.345.678-5") },
                singleLine = true,
                isError = errorRut != null,
                supportingText = { errorRut?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it.trim() },
                label = { Text("Correo electrónico") },
                singleLine = true,
                isError = errorCorreo != null,
                supportingText = { errorCorreo?.let { Text(it) } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = clave,
                onValueChange = { clave = it },
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                isError = errorClave != null,
                supportingText = { errorClave?.let { Text(it) } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = confirmacion,
                onValueChange = { confirmacion = it },
                label = { Text("Confirmar contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                isError = errorConfirmacion != null,
                supportingText = { errorConfirmacion?.let { Text(it) } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            Text("Rol")

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Rol.entries.forEach { rol ->
                    RadioButton(
                        selected = rolSeleccionado == rol.name,
                        onClick = {
                            rolSeleccionado = rol.name
                            registroViewModel.limpiarResultado()
                        }
                    )
                    Text(rol.etiqueta)
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = aceptaTerminos,
                    onCheckedChange = { aceptaTerminos = it }
                )

                Text("Acepto los términos y condiciones")
            }

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = {
                    intentoEnviar = true
                    val formularioValido = validarNombre(nombre) == null &&
                            validarRut(rut) == null &&
                            validarEmail(correo) == null &&
                            validarPassword(clave) == null &&
                            validarConfirmacion(clave, confirmacion) == null &&
                            aceptaTerminos
                    if (formularioValido) {
                        registroViewModel.registrar(nombre, rut, correo, clave, Rol.valueOf(rolSeleccionado))
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
                    Text("Registrar")
                }
            }

            val errorServidor = resultado as? ResultadoRegistro.Error
            if (errorServidor != null) {
                Text(
                    text = errorServidor.mensaje,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            if (!aceptaTerminos) {
                Text(
                    text = "Debes aceptar los términos para registrarte.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
