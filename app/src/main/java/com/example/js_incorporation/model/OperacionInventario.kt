package com.example.js_incorporation.model

open class OperacionInventario(
    val codigoProducto: String,
    val cantidad: Int
) {
    open fun obtenerTipo(): String {
        return "Operación de inventario"
    }
}

class EntradaInventario(
    codigoProducto: String,
    cantidad: Int
) : OperacionInventario(codigoProducto, cantidad) {

    override fun obtenerTipo(): String {
        return "Entrada"
    }
}

class SalidaInventario(
    codigoProducto: String,
    cantidad: Int
) : OperacionInventario(codigoProducto, cantidad) {

    override fun obtenerTipo(): String {
        return "Salida"
    }
}