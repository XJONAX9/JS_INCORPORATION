
package com.example.js_incorporation.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.js_incorporation.components.ImagenInteligente
import com.example.js_incorporation.navigation.Screen
import com.example.js_incorporation.viewmodels.MainViewModel
import com.example.js_incorporation.viewmodels.PerfilViewModel
import java.io.File

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
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                16.dp,
                Alignment.CenterVertically
            )
        ) {
            Text("Perfil de usuario")
            Text("Bienvenido a Kaiju Inventario")

            ImagenInteligente(imagenUri = imagenUri)

            Button(
                onClick = {
                    galeriaLauncher.launch("image/*")
                }
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
                }
            ) {
                Text("Tomar fotografía")
            }

            Button(
                onClick = {
                    viewModel.navigateTo(Screen.Inventario)
                }
            ) {
                Text("Ir al inventario")
            }
        }
    }
}

