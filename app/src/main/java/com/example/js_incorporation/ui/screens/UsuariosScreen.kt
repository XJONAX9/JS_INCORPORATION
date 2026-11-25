package com.example.js_incorporation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.js_incorporation.model.Rol
import com.example.js_incorporation.model.esAdmin
import com.example.js_incorporation.network.UsuarioKaijuResponse
import com.example.js_incorporation.viewmodels.MainViewModel
import com.example.js_incorporation.viewmodels.UsuariosViewModel

private val AzulOscuro = Color(0xFF101A30)
private val AzulTarjeta = Color(0xFF1D2A42)
private val Blanco = Color(0xFFF4F6FA)

@Composable
fun UsuariosScreen(
    viewModel: MainViewModel,
    alVolver: () -> Unit,
    alEliminarCuentaPropia: () -> Unit,
    usuariosViewModel: UsuariosViewModel = viewModel()
) {
    val actual by viewModel.usuarioActual.collectAsState()
    val usuarios by usuariosViewModel.usuarios.collectAsState()
    val cargando by usuariosViewModel.cargando.collectAsState()
    val mensaje by usuariosViewModel.mensaje.collectAsState()

    var usuarioAEliminar by remember { mutableStateOf<UsuarioKaijuResponse?>(null) }

    LaunchedEffect(Unit) {
        usuariosViewModel.cargar()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AzulOscuro)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Usuarios registrados",
            color = Blanco,
            fontSize = 25.sp,
            style = MaterialTheme.typography.headlineSmall
        )

        if (!actual.esAdmin) {
            Text(
                text = "Solo un administrador puede ver esta sección.",
                color = Color(0xFFFF8A80)
            )
        } else {
            Text(
                text = "Total: ${usuarios.size}",
                color = Color.LightGray
            )

            if (mensaje != null) {
                Text(
                    text = mensaje ?: "",
                    color = Color(0xFF81C784)
                )
            }

            if (cargando && usuarios.isEmpty()) {
                CircularProgressIndicator(color = Blanco)
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(usuarios) { usuario ->
                    val esYo = usuario.email.equals(actual?.email, ignoreCase = true)
                    val esAdminCuenta = usuario.rol == Rol.ADMIN.name

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = AzulTarjeta)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = if (esYo) "${usuario.nombreCompleto} (tú)" else usuario.nombreCompleto,
                                color = Blanco,
                                fontSize = 18.sp
                            )
                            Text(
                                text = usuario.email,
                                color = Color.LightGray,
                                fontSize = 13.sp
                            )
                            Text(
                                text = "RUT: ${usuario.rut}",
                                color = Color.LightGray,
                                fontSize = 13.sp
                            )
                            Text(
                                text = "Rol: ${runCatching { Rol.valueOf(usuario.rol).etiqueta }.getOrDefault(usuario.rol)}",
                                color = if (esAdminCuenta) Color(0xFFFFD54F) else Blanco
                            )

                            Button(
                                onClick = { usuarioAEliminar = usuario },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFD32F2F)
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = if (esYo) "Eliminar mi cuenta" else "Eliminar",
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }

        TextButton(
            onClick = alVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Volver",
                color = Blanco
            )
        }
    }

    if (usuarioAEliminar != null) {
        AlertDialog(
            onDismissRequest = { usuarioAEliminar = null },
            title = { Text("Eliminar cuenta") },
            text = {
                val propia = usuarioAEliminar?.email.equals(actual?.email, ignoreCase = true)
                Text(
                    if (propia) {
                        "¿Seguro que quieres eliminar tu cuenta? Se cerrará tu sesión y no se puede deshacer."
                    } else {
                        "¿Seguro que quieres eliminar la cuenta de " +
                                "${usuarioAEliminar?.nombreCompleto}? Esta acción no se puede deshacer."
                    }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        usuarioAEliminar?.let { usuario ->
                            usuariosViewModel.limpiarMensaje()
                            usuariosViewModel.eliminar(
                                id = usuario.id,
                                esCuentaPropia = usuario.email.equals(actual?.email, ignoreCase = true),
                                alEliminarCuentaPropia = alEliminarCuentaPropia
                            )
                        }
                        usuarioAEliminar = null
                    }
                ) {
                    Text("Sí, eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { usuarioAEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
