package com.example.js_incorporation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.js_incorporation.model.Rol
import com.example.js_incorporation.model.Usuario
import com.example.js_incorporation.network.UsuarioKaijuResponse
import com.example.js_incorporation.repository.AuthException
import com.example.js_incorporation.repository.AuthRepository
import com.example.js_incorporation.repository.SesionKaiju
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ResultadoLogin {
    data class Exito(val usuario: Usuario) : ResultadoLogin()
    data class Error(val mensaje: String) : ResultadoLogin()
}

class LoginViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _resultado = MutableStateFlow<ResultadoLogin?>(null)
    val resultado: StateFlow<ResultadoLogin?> = _resultado.asStateFlow()

    fun iniciarSesion(email: String, password: String) {
        viewModelScope.launch {
            if (_isLoading.value) return@launch
            _isLoading.value = true
            _resultado.value = null

            try {
                val respuesta = repository.login(email, password)
                SesionKaiju.iniciar(respuesta.accessToken)
                _resultado.value = ResultadoLogin.Exito(respuesta.usuario.aUsuario())
            } catch (e: AuthException) {
                _resultado.value = ResultadoLogin.Error(e.message ?: "No se pudo iniciar sesión")
            } catch (e: Exception) {
                _resultado.value = ResultadoLogin.Error("No se pudo iniciar sesión")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun limpiarResultado() {
        _resultado.value = null
    }
}

fun UsuarioKaijuResponse.aUsuario(): Usuario = Usuario(
    rut = rut,
    nombreCompleto = nombreCompleto,
    email = email,
    rol = runCatching { Rol.valueOf(rol) }.getOrDefault(Rol.BODEGA)
)
