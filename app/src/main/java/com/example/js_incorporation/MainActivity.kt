package com.example.js_incorporation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.js_incorporation.navigation.NavigationEvent
import com.example.js_incorporation.model.esAdmin
import com.example.js_incorporation.navigation.Screen
import com.example.js_incorporation.repository.SesionKaiju
import com.example.js_incorporation.ui.screens.UsuariosScreen
import com.example.js_incorporation.ui.screens.HomeScreen
import com.example.js_incorporation.ui.screens.InventarioScreen
import com.example.js_incorporation.ui.screens.PerfilScreen
import com.example.js_incorporation.ui.screens.RegistroScreen
import com.example.js_incorporation.ui.screens.ModoEspecialScreen
import com.example.js_incorporation.ui.screens.RegistroProductoScreen
import com.example.js_incorporation.ui.screens.MovimientoScreen
import com.example.js_incorporation.ui.screens.HistorialMovimientosScreen
import com.example.js_incorporation.ui.theme.JS_INCORPORATIONTheme
import com.example.js_incorporation.viewmodels.MainViewModel
import com.example.js_incorporation.viewmodels.ProductoViewModel
import com.example.js_incorporation.viewmodels.EstadoViewModel
import kotlinx.coroutines.flow.collectLatest
import com.example.js_incorporation.ui.screens.BienvenidaScreen
import com.example.js_incorporation.ui.screens.LoginScreen
import com.example.js_incorporation.ui.screens.CatalogoExternoScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JS_INCORPORATIONTheme {
                val viewModel: MainViewModel = viewModel()
                val productoViewModel: ProductoViewModel = viewModel()
                val navController = rememberNavController()
                val estadoViewModel: EstadoViewModel = viewModel()
                val usuarioActual by viewModel.usuarioActual.collectAsState()
                val context = LocalContext.current

                LaunchedEffect(Unit) {
                    SesionKaiju.sesionExpirada.collect {
                        viewModel.cerrarSesion()
                        productoViewModel.limpiar()
                        Toast.makeText(
                            context,
                            "Tu sesión terminó. Inicia sesión nuevamente.",
                            Toast.LENGTH_LONG
                        ).show()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    }
                }

                LaunchedEffect(Unit) {
                    viewModel.navigationEvents.collectLatest { event ->
                        when (event) {
                            is NavigationEvent.NavigateTo -> {
                                navController.navigate(event.route.route) {
                                    event.popupToRoute?.let { screen ->
                                        popUpTo(screen.route) {
                                            inclusive = event.inclusive
                                        }
                                    }
                                    launchSingleTop = event.singleTop
                                }
                            }

                            NavigationEvent.PopBackStack -> {
                                navController.popBackStack()
                            }

                            NavigationEvent.NavigateUp -> {
                                navController.navigateUp()
                            }
                        }
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Bienvenida.route
                    ) {
                        composable(Screen.Bienvenida.route) {
                            BienvenidaScreen(
                                onIniciarClick = {
                                    navController.navigate(Screen.Login.route)
                                }
                            )
                        }

                        composable(Screen.Login.route) {
                            LoginScreen(
                                viewModel = viewModel,

                                onLoginClick = {
                                    productoViewModel.cargarProductosDesdeApi()
                                    estadoViewModel.recargar()
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Login.route) {
                                            inclusive = true
                                        }
                                    }
                                },

                                onRegistrarseClick = {
                                    navController.navigate("registro")
                                }
                            )
                        }

                        composable("registro") {
                            RegistroScreen(
                                navController = navController
                            )
                        }

                        composable(Screen.Home.route) {
                            HomeScreen(viewModel = viewModel)
                        }

                        composable(Screen.CatalogoExterno.route) {
                            CatalogoExternoScreen()
                        }

                        composable(Screen.Inventario.route) {
                            InventarioScreen(
                                viewModel = viewModel,
                                productoViewModel = productoViewModel,
                                estadoViewModel = estadoViewModel,
                                alRegistrar = {
                                    navController.navigate(
                                        Screen.RegistroProducto.route
                                    )
                                }
                            )
                        }

                        composable(Screen.RegistroProducto.route) {
                            RegistroProductoScreen(
                                productoViewModel = productoViewModel,
                                alRegistrar = {
                                    navController.popBackStack()
                                },

                                alVolver = {
                                    navController.popBackStack()
                                }

                            )
                        }

                        composable(Screen.Perfil.route) {
                            PerfilScreen(
                                viewModel = viewModel,
                                onCerrarSesion = {
                                    viewModel.cerrarSesion()
                                    productoViewModel.limpiar()
                                    navController.navigate(Screen.Login.route) {
                                        popUpTo(navController.graph.id) {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }

                        composable(Screen.Usuarios.route) {
                            UsuariosScreen(
                                viewModel = viewModel,
                                alVolver = {
                                    navController.popBackStack()
                                },
                                alEliminarCuentaPropia = {
                                    viewModel.cerrarSesion()
                                    productoViewModel.limpiar()
                                    navController.navigate(Screen.Login.route) {
                                        popUpTo(navController.graph.id) {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }

                        composable(Screen.ModoEspecial.route) {
                            ModoEspecialScreen(
                                viewModel = estadoViewModel,
                                puedeEditar = usuarioActual.esAdmin
                            )
                        }

                        composable(Screen.Movimientos.route) {
                            MovimientoScreen(
                                productoViewModel = productoViewModel,
                                alVolver = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable(Screen.HistorialMovimientos.route) {
                            HistorialMovimientosScreen(
                                mostrarAutor = usuarioActual.esAdmin,
                                productoViewModel = productoViewModel,
                                alVolver = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
