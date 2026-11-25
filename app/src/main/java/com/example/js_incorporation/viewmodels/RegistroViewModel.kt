package com.example.js_incorporation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.js_incorporation.model.Rol
import com.example.js_incorporation.repository.AuthException
import com.example.js_incorporation.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ResultadoRegistro {
    data object Exito : ResultadoRegistro()
    data class Error(val mensaje: String) : ResultadoRegistro()
}

class RegistroViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _resultado = MutableStateFlow<ResultadoRegistro?>(null)
    val resultado: StateFlow<ResultadoRegistro?> = _resultado.asStateFlow()

    fun registrar(nombre: String, rut: String, email: String, password: String, rol: Rol) {
        viewModelScope.launch {
            if (_isLoading.value) return@launch
            _isLoading.value = true
            _resultado.value = null

            try {
                repository.registrar(
                    nombreCompleto = nombre,
                    rut = rut,
                    email = email,
                    password = password,
                    rol = rol.name
                )
                _resultado.value = ResultadoRegistro.Exito
            } catch (e: AuthException) {
                _resultado.value = ResultadoRegistro.Error(e.message ?: "No se pudo registrar")
            } catch (e: Exception) {
                _resultado.value = ResultadoRegistro.Error("No se pudo registrar")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun limpiarResultado() {
        _resultado.value = null
    }
}
