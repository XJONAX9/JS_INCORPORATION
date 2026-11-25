package com.example.js_incorporation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.js_incorporation.data.EstadoDataStore
import com.example.js_incorporation.network.ConfiguracionDto
import com.example.js_incorporation.network.RetrofitClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.HttpException

class EstadoViewModel(application: Application) : AndroidViewModel(application) {
    private val estadoDataStore = EstadoDataStore(application)

    private val _activo = MutableStateFlow<Boolean?>(null)
    val activo: StateFlow<Boolean?> = _activo

    private val _mostrarMensaje = MutableStateFlow(false)
    val mostrarMensaje: StateFlow<Boolean> = _mostrarMensaje

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        cargarEstado(conRetraso = true)
    }

    fun recargar() {
        cargarEstado(conRetraso = false)
    }

    private fun cargarEstado(conRetraso: Boolean) {
        viewModelScope.launch {
            if (conRetraso) delay(1500)
            val local = estadoDataStore.obtenerEstado().first()
            _activo.value = try {
                val remoto = RetrofitClient.configuracionApi.obtener().modoEspecial
                estadoDataStore.guardarEstado(remoto)
                remoto
            } catch (e: Exception) {
                _activo.value ?: local ?: false
            }
        }
    }

    fun alternarEstado() {
        viewModelScope.launch {
            val nuevoValor = !(_activo.value ?: false)

            try {
                val guardado = RetrofitClient.configuracionApi
                    .actualizar(ConfiguracionDto(nuevoValor))
                    .modoEspecial
                estadoDataStore.guardarEstado(guardado)
                _activo.value = guardado
                _error.value = null

                _mostrarMensaje.value = true
                delay(2000)
                _mostrarMensaje.value = false
            } catch (e: HttpException) {
                _error.value = if (e.code() == 403) {
                    "Solo un administrador puede cambiar esta configuración"
                } else {
                    "No se pudo guardar el cambio (error ${e.code()})"
                }
            } catch (e: Exception) {
                _error.value = "No se pudo guardar el cambio. Revisa tu conexión."
            }
        }
    }

    fun limpiarError() {
        _error.value = null
    }
}
