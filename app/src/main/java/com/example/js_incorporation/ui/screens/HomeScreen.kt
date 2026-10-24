package com.example.js_incorporation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.js_incorporation.navigation.Screen
import com.example.js_incorporation.viewmodels.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: MainViewModel) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Menú Kaiju",
                    modifier = Modifier.padding(16.dp)
                )

                Button(onClick = {
                    scope.launch { drawerState.close() }
                    viewModel.navigateTo(Screen.Inventario)
                }) {
                    Text("Ver inventario")
                }

                Button(onClick = {
                    scope.launch { drawerState.close() }
                    viewModel.navigateTo(Screen.Perfil)
                }) {
                    Text("Perfil")
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Kaiju Inventario") },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch { drawerState.open() }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("¡Bienvenido a Kaiju Inventario!")

                Button(
                    onClick = {
                        viewModel.navigateTo(Screen.Inventario)
                    }
                ) {
                    Text("Ver inventario")
                }

                Button(
                    onClick = {
                        viewModel.navigateTo(Screen.Inventario)
                    }
                ) {
                    Text("Registrar producto")
                }

                Button(
                    onClick = {
                        viewModel.navigateTo(Screen.ModoEspecial)
                    }
                ) {
                    Text("Modo especial")
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
