package com.example.js_incorporation.repository

object SesionKaiju {

    var token: String? = null
        private set

    fun iniciar(nuevoToken: String) {
        token = nuevoToken
    }

    fun cerrar() {
        token = null
    }
}
