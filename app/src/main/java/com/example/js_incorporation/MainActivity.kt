
package com.example.js_incorporation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.js_incorporation.navigation.NavigationEvent
import com.example.js_incorporation.navigation.Screen
import com.example.js_incorporation.ui.screens.HomeScreen
import com.example.js_incorporation.ui.screens.InventarioScreen
import com.example.js_incorporation.ui.screens.PerfilScreen
import com.example.js_incorporation.ui.theme.JS_INCORPORATIONTheme
import com.example.js_incorporation.viewmodels.MainViewModel
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JS_INCORPORATIONTheme {

                val viewModel: MainViewModel = viewModel()
                val navController = rememberNavController()

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
                        startDestination = Screen.Home.route
                    ) {
                        composable(Screen.Home.route) {
                            HomeScreen(viewModel = viewModel)
                        }

                        composable(Screen.Inventario.route) {
                            InventarioScreen(viewModel = viewModel)
                        }

                        composable(Screen.Perfil.route) {
                            PerfilScreen(viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}
