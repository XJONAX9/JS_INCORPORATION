package com.example.js_incorporation.repository

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object SesionKaiju {
    @Volatile
    var token: String? = null
        private set

    private val _sesionExpirada = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val sesionExpirada: SharedFlow<Unit> = _sesionExpirada.asSharedFlow()

    fun iniciar(nuevoToken: String) {
        token = nuevoToken
    }

    fun cerrar() {
        token = null
    }

    @Synchronized
    fun expirar() {
        if (token == null) return
        token = null
        _sesionExpirada.tryEmit(Unit)
    }
}
