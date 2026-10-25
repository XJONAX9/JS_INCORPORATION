package com.example.js_incorporation.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.js_incorporation.components.ImagenInteligente
import com.example.js_incorporation.navigation.Screen
import com.example.js_incorporation.viewmodels.MainViewModel
import com.example.js_incorporation.viewmodels.PerfilViewModel
import java.io.File

private val AzulOscuro = Color(0xFF101A30)
private val AzulTarjeta = Color(0xFF1D2A42)
private val Blanco = Color(0xFFF4F6FA)

@Composable
fun PerfilScreen(
    viewModel: MainViewModel,
    perfilViewModel: PerfilViewModel = viewModel()
) {
    val imagenUri by perfilViewModel.imagenUri.collectAsState()
    val context = LocalContext.current

    val galeriaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            perfilViewModel.actualizarImagenGaleria(uri)
        }
    }

    val archivoFoto = remember {
        File(context.cacheDir, "foto_perfil.jpg")
    }

    val uriCamara = remember {
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            archivoFoto
        )
    }

    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { guardada ->
        if (guardada) {
            perfilViewModel.actualizarImagenCamara(uriCamara)
        }
    }

    val permisoCamaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) {
            camaraLauncher.launch(uriCamara)
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        viewModel.navigateTo(Screen.Home)
                    },
                    icon = { Text("🏠") },
                    label = { Text("Inicio") }
                )

                NavigationBarItem(
                    selected = true,
                    onClick = {
                        viewModel.navigateTo(Screen.Perfil)
                    },
                    icon = { Text("👤") },
                    label = { Text("Perfil") }
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AzulOscuro)
                .padding(innerPadding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Text(
                text = "Mi perfil",
                fontSize = 28.sp,
                color = Blanco
            )

            Text(
                text = "Kaiju Inventario",
                fontSize = 16.sp,
                color = Blanco
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AzulTarjeta
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    ImagenInteligente(imagenUri = imagenUri)

                    Text(
                        text = "Foto de perfil",
                        color = Blanco,
                        fontSize = 20.sp
                    )

                    Text(
                        text = "Selecciona una imagen o toma una fotografía.",
                        color = Blanco
                    )

                    Button(
                        onClick = {
                            galeriaLauncher.launch("image/*")
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Seleccionar desde galería")
                    }

                    Button(
                        onClick = {
                            val permisoConcedido =
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CAMERA
                                ) == PackageManager.PERMISSION_GRANTED

                            if (permisoConcedido) {
                                camaraLauncher.launch(uriCamara)
                            } else {
                                permisoCamaraLauncher.launch(
                                    Manifest.permission.CAMERA
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Tomar fotografía")
                    }
                }
            }

            Button(
                onClick = {
                    viewModel.navigateTo(Screen.Inventario)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ir al inventario")
            }
        }
    }
}


