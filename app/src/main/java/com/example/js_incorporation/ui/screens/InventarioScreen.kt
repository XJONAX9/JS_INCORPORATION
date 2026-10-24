package com.example.js_incorporation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.js_incorporation.navigation.Screen
import com.example.js_incorporation.viewmodels.MainViewModel

@Composable
fun InventarioScreen(viewModel: MainViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text("Inventario de Kaiju")

        Text("Aquí se mostrarán los productos registrados.")

        Button(
            onClick = {
                viewModel.navigateTo(Screen.Home)
            }
        ) {
            Text("Volver al inicio")
        }

        Button(
            onClick = {
                viewModel.navigateTo(Screen.Perfil)
            }
        ) {
            Text("Ir al perfil")
        }
    }
}

