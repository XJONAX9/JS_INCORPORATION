package com.example.js_incorporation.navigation

sealed class Screen(val route: String) {

    data object Home : Screen("home_page")

    data object Inventario : Screen("inventario_page")

    data object Perfil : Screen("perfil_page")

    data object ModoEspecial : Screen("modo_especial")

    data object RegistroProducto : Screen("registro_producto")

    data object Movimientos : Screen("movimientos_page")

}