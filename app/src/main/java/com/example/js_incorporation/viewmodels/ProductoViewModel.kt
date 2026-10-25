package com.example.js_incorporation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.js_incorporation.data.ProductoDataStore
import com.example.js_incorporation.model.Producto
import com.example.js_incorporation.model.OperacionInventario
import com.example.js_incorporation.model.EntradaInventario
import com.example.js_incorporation.model.SalidaInventario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProductoViewModel(application: Application) : AndroidViewModel(application) {

    private val productoDataStore = ProductoDataStore(application)

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos = _productos.asStateFlow()

    private val _movimientos = MutableStateFlow<List<Movimiento>>(emptyList())
    val movimientos = _movimientos.asStateFlow()

    init {
        cargarProductos()
    }

    private fun cargarProductos() {
        viewModelScope.launch {
            val guardados = productoDataStore.obtenerProductos().first()

            _productos.value = guardados ?: listOf(
                Producto("KJ001", "Poleron negro", "Vestuario", 15, 5),
                Producto("KJ002", "Polera blanca", "Vestuario", 25, 5),
                Producto("KJ003", "Mouse inalambrico", "Electronica", 8, 5),
                Producto("KJ004", "Audifonos", "Electronica", 12, 5),
                Producto("KJ005", "Cable USB-C", "Accesorios", 4, 5),
                Producto("KJ006", "Caja de embalaje", "Materiales", 30, 10)
            )

            if (guardados == null) {
                guardarProductos()
            }
        }
    }

    private fun guardarProductos() {
        viewModelScope.launch {
            productoDataStore.guardarProductos(_productos.value)
        }
    }

    fun registrarProducto(producto: Producto) {
        if (_productos.value.any {
                it.codigo.equals(producto.codigo, ignoreCase = true)
            }) {
            return
        }

        _productos.value = _productos.value + producto
        guardarProductos()
    }

    fun eliminarProducto(producto: Producto) {
        _productos.value = _productos.value.filter {
            it.codigo != producto.codigo
        }

        guardarProductos()
    }

    fun editarProducto(productoActualizado: Producto) {
        _productos.value = _productos.value.map { producto ->
            if (producto.codigo == productoActualizado.codigo) {
                productoActualizado
            } else {
                producto
            }
        }

        guardarProductos()
    }

    fun registrarMovimiento(
        codigo: String,
        cantidad: Int,
        esEntrada: Boolean
    ) {
        if (cantidad <= 0) return

        val producto = _productos.value.find {
            it.codigo.equals(codigo, ignoreCase = true)
        } ?: return

        // Crear la operación según el tipo de movimiento.
        val operacion: OperacionInventario =
            if (esEntrada) {
                EntradaInventario(producto.codigo, cantidad)
            } else {
                SalidaInventario(producto.codigo, cantidad)
            }

        // Calcular el nuevo stock usando el tipo de operación.
        val nuevoStock = when (operacion.obtenerTipo()) {
            "Entrada" -> producto.stock + operacion.cantidad
            "Salida" -> producto.stock - operacion.cantidad
            else -> return
        }

        // No permitir que el stock quede negativo.
        if (nuevoStock < 0) return

        _productos.value = _productos.value.map {
            if (it.codigo == producto.codigo) {
                it.copy(stock = nuevoStock)
            } else {
                it
            }
        }

        guardarProductos()

        val movimiento = Movimiento(
            codigoProducto = producto.codigo,
            nombreProducto = producto.nombre,
            tipo = operacion.obtenerTipo(),
            cantidad = operacion.cantidad,
            fecha = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale.getDefault()
            ).format(Date()),
            stockResultante = nuevoStock,
            stockMinimo = producto.stockMinimo
        )

        _movimientos.value = listOf(movimiento) + _movimientos.value
    }
}


