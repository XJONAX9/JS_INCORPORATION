package com.example.js_incorporation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.js_incorporation.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.js_incorporation.network.RetrofitClient
import com.example.js_incorporation.network.MovimientoRequest
import com.example.js_incorporation.model.MovimientoDto

class ProductoViewModel(application: Application) : AndroidViewModel(application) {


    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos = _productos.asStateFlow()


    private val _movimientosApi =
        MutableStateFlow<List<MovimientoDto>>(emptyList())

    val movimientosApi =
        _movimientosApi.asStateFlow()

    init {
        cargarProductosDesdeApi()


    }


    fun cargarProductosDesdeApi() {
        viewModelScope.launch {
            try {
                val productosApi =
                    RetrofitClient.productoApi.obtenerProductos()
                _productos.value = productosApi
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun cargarMovimientosDesdeApi() {
        viewModelScope.launch {
            try {
                val movimientos =
                    RetrofitClient
                        .movimientoApi
                        .obtenerMovimientos()

                _movimientosApi.value = movimientos
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun registrarProductoDesdeApi(
        producto: Producto
    ) {
        viewModelScope.launch {
            try {
                RetrofitClient
                    .productoApi
                    .registrarProducto(producto)
                cargarProductosDesdeApi()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun eliminarProductoDesdeApi(
        codigo: String
    ) {
        viewModelScope.launch {
            try {
                RetrofitClient
                    .productoApi
                    .eliminarProducto(codigo)
                cargarProductosDesdeApi()
                println("ELIMINADO Y RECARGADO")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun editarProductoDesdeApi(
        productoActualizado: Producto
    ) {
        viewModelScope.launch {
            try {
                RetrofitClient
                    .productoApi
                    .editarProducto(
                        productoActualizado.codigo,
                        productoActualizado
                    )
                cargarProductosDesdeApi()
            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }

    fun registrarMovimientoDesdeApi(
        codigoProducto: String,
        cantidad: Int,
        esEntrada: Boolean
    ) {
        viewModelScope.launch {
            try {
                val movimiento = MovimientoRequest(
                    tipoMovimiento =
                        if (esEntrada) "ENTRADA" else "SALIDA",
                    cantidad = cantidad,
                    fecha = java.time.LocalDate.now().toString(),
                    codigoProducto = codigoProducto
                )
                RetrofitClient
                    .movimientoApi
                    .registrarMovimiento(movimiento)

                cargarProductosDesdeApi()
                cargarMovimientosDesdeApi()
            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }
}


