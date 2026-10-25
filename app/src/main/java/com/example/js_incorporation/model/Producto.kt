package com.example.js_incorporation.model

data class Producto(
    val codigo: String,
    val nombre: String,
    val categoria: String,
    val stock: Int,
    val stockMinimo: Int
)