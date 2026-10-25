package com.example.js_incorporation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.js_incorporation.navigation.Screen
import com.example.js_incorporation.viewmodels.MainViewModel
import kotlinx.coroutines.launch

private val AzulOscuro = Color(0xFF101A30)
private val AzulTarjeta = Color(0xFF1D2A42)
private val Blanco = Color(0xFFF4F6FA)

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
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(20.dp)
                )

                TextButton(
                    onClick = {
                        scope.launch { drawerState.close() }
                        viewModel.navigateTo(Screen.Inventario)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Inventory2, contentDescription = null)
                    Text("  Inventario")
                }

                TextButton(
                    onClick = {
                        scope.launch { drawerState.close() }
                        viewModel.navigateTo(Screen.Perfil)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Person, contentDescription = null)
                    Text("  Perfil")
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Kaiju Inventario",
                            fontWeight = FontWeight.Bold
                        )
                    },
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
                    .background(AzulOscuro)
                    .padding(innerPadding)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Text(
                    text = "¡Bienvenido a Kaiju!",
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold,
                    color = Blanco
                )

                Text(
                    text = "Gestiona tus productos y controla el inventario.",
                    fontSize = 15.sp,
                    color = Blanco
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AzulTarjeta
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Inventory2,
                                contentDescription = null,
                                tint = Blanco
                            )
                            Text(
                                text = "  Gestión de inventario",
                                color = Blanco,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            text = "Consulta tus productos y revisa su stock.",
                            color = Blanco
                        )

                        Button(
                            onClick = {
                                viewModel.navigateTo(Screen.Inventario)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Ver inventario")
                        }
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AzulTarjeta
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AddBox,
                                contentDescription = null,
                                tint = Blanco
                            )
                            Text(
                                text = "  Registrar producto",
                                color = Blanco,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            text = "Agrega nuevos productos al inventario.",
                            color = Blanco
                        )

                        Button(
                            onClick = {
                                viewModel.navigateTo(Screen.RegistroProducto)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Registrar producto")
                        }
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AzulTarjeta
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = null,
                                tint = Blanco
                            )
                            Text(
                                text = "  Modo especial",
                                color = Blanco,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            text = "Accede a las opciones especiales de la aplicación.",
                            color = Blanco
                        )

                        Button(
                            onClick = {
                                viewModel.navigateTo(Screen.ModoEspecial)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Abrir modo especial")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

