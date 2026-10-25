package com.example.js_incorporation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.js_incorporation.ui.screens.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductoViewModel : ViewModel() {

    private val _productos = MutableStateFlow(
        listOf(
            Producto("KJ001", "Poleron negro", "Vestuario", 15, 5),
            Producto("KJ002", "Polera blanca", "Vestuario", 25, 5),
            Producto("KJ003", "Mouse inalambrico", "Electronica", 8, 5),
            Producto("KJ004", "Audifonos", "Electronica", 12, 5),
            Producto("KJ005", "Cable USB-C", "Accesorios", 4, 5),
            Producto("KJ006", "Caja de embalaje", "Materiales", 30, 10)
        )
    )

    val productos = _productos.asStateFlow()


    fun registrarProducto(producto: Producto) {
        _productos.value = _productos.value + producto
    }


    fun eliminarProducto(producto: Producto) {
        _productos.value = _productos.value.filter {
            it.codigo != producto.codigo
        }
    }


    fun editarProducto(productoActualizado: Producto) {
        _productos.value = _productos.value.map { producto ->
            if (producto.codigo == productoActualizado.codigo) {
                productoActualizado
            } else {
                producto
            }
        }
    }



    fun registrarMovimiento(codigo: String, cantidad: Int, esEntrada: Boolean) {
        _productos.value = _productos.value.map { producto ->
            if (producto.codigo == codigo) {
                val nuevoStock = if (esEntrada) {
                    producto.stock + cantidad
                } else {
                    producto.stock - cantidad
                }

                if (nuevoStock >= 0) {
                    producto.copy(stock = nuevoStock)
                } else {
                    producto
                }

            } else {
                producto
            }
        }
    }

}