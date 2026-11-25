package com.example.js_incorporation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.js_incorporation.network.UsuarioKaijuResponse
import com.example.js_incorporation.repository.AuthException
import com.example.js_incorporation.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsuariosViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {
    private val _usuarios = MutableStateFlow<List<UsuarioKaijuResponse>>(emptyList())
    val usuarios: StateFlow<List<UsuarioKaijuResponse>> = _usuarios.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    fun cargar() {
        viewModelScope.launch {
            _cargando.value = true
            try {
                _usuarios.value = repository.listarUsuarios()
            } catch (e: AuthException) {
                _mensaje.value = e.message
            } catch (e: Exception) {
                _mensaje.value = "No se pudieron cargar los usuarios"
            } finally {
                _cargando.value = false
            }
        }
    }

    fun eliminar(id: Long, esCuentaPropia: Boolean, alEliminarCuentaPropia: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.eliminarUsuario(id)
                if (esCuentaPropia) {
                    alEliminarCuentaPropia()
                    return@launch
                }
                _mensaje.value = "Usuario eliminado"
                _usuarios.value = repository.listarUsuarios()
            } catch (e: AuthException) {
                _mensaje.value = e.message
            } catch (e: Exception) {
                _mensaje.value = "No se pudo eliminar el usuario"
            }
        }
    }

    fun limpiarMensaje() {
        _mensaje.value = null
    }
}
