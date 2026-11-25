package com.example.js_incorporation.model

data class MovimientoDto(
    val id: Long,
    val tipoMovimiento: String,
    val cantidad: Int,
    val fecha: String,
    val codigoProducto: String,
    val registradoPor: String? = null
)