package com.example.js_incorporation.model

enum class Rol(val etiqueta: String) {
    BODEGA("Bodega"),
    VENTAS("Ventas"),
    ADMIN("Admin")
}

data class Usuario(
    val rut: String,
    val nombreCompleto: String,
    val email: String,
    val rol: Rol
)
