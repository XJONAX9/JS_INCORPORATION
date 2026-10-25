package com.example.js_incorporation.viewmodels

data class Movimiento(
    val codigoProducto: String,
    val nombreProducto: String,
    val tipo: String,
    val cantidad: Int,
    val fecha: String,
    val stockResultante: Int,
    val stockMinimo: Int
)