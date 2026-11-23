package com.example.js_incorporation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.js_incorporation.model.ProductoExterno
import com.example.js_incorporation.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductoExternoViewModel : ViewModel() {
    private val _productos =
        MutableStateFlow<List<ProductoExterno>>(emptyList())
    val productos = _productos.asStateFlow()
    init {
        cargarProductos()
    }
    fun cargarProductos() {
        viewModelScope.launch {
            try {
                _productos.value =
                    RetrofitClient
                        .fakeStoreApi
                        .obtenerProductos()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
