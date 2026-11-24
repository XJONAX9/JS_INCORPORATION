package com.example.js_incorporation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.js_incorporation.model.Usuario
import com.example.js_incorporation.navigation.NavigationEvent
import com.example.js_incorporation.navigation.Screen
import com.example.js_incorporation.repository.SesionKaiju
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()

    val navigationEvents = _navigationEvents.asSharedFlow()

    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual.asStateFlow()

    fun iniciarSesion(usuario: Usuario) {
        _usuarioActual.value = usuario
    }

    fun cerrarSesion() {
        _usuarioActual.value = null
        SesionKaiju.cerrar()
    }

    fun navigateTo(screen: Screen) {
        viewModelScope.launch {
            _navigationEvents.emit(
                NavigationEvent.NavigateTo(route = screen)
            )
        }
    }

    fun navigateBack() {
        viewModelScope.launch {
            _navigationEvents.emit(NavigationEvent.PopBackStack)
        }
    }

    fun navigateUp() {
        viewModelScope.launch {
            _navigationEvents.emit(NavigationEvent.NavigateUp)
        }
    }
}
