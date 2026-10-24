package com.example.js_incorporation.model

data class UsuarioUiState(
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val direccion: String = "",
    val errores: UsuarioErrores = UsuarioErrores(),
    val aceptaTerminos: Boolean = false
)
