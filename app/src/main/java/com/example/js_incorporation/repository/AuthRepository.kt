package com.example.js_incorporation.repository

import com.example.js_incorporation.network.KaijuAuthApi
import com.example.js_incorporation.network.LoginKaijuRequest
import com.example.js_incorporation.network.LoginKaijuResponse
import com.example.js_incorporation.network.RegistroKaijuRequest
import com.example.js_incorporation.network.RetrofitClient
import com.example.js_incorporation.network.UsuarioKaijuResponse
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

class AuthException(mensaje: String) : Exception(mensaje)

class AuthRepository(
    private val api: KaijuAuthApi = RetrofitClient.kaijuAuthApi
) {

    suspend fun login(email: String, password: String): LoginKaijuResponse =
        llamar(mensaje401 = "Email o contraseña incorrectos") {
            api.login(LoginKaijuRequest(email.trim().lowercase(), password))
        }

    suspend fun registrar(
        nombreCompleto: String,
        rut: String,
        email: String,
        password: String,
        rol: String
    ): UsuarioKaijuResponse =
        llamar {
            api.registrar(
                RegistroKaijuRequest(
                    nombreCompleto = nombreCompleto.trim(),
                    rut = rut.trim(),
                    email = email.trim().lowercase(),
                    password = password,
                    rol = rol
                )
            )
        }

    private suspend fun <T> llamar(mensaje401: String = "No autorizado", bloque: suspend () -> T): T =
        try {
            bloque()
        } catch (e: HttpException) {
            throw AuthException(mensajeDeError(e, mensaje401))
        } catch (e: IOException) {
            throw AuthException("No se pudo conectar con el servidor. Revisa tu conexión.")
        }

    private fun mensajeDeError(e: HttpException, mensaje401: String): String {
        if (e.code() == 401) return mensaje401
        val delServidor = leerMensaje(e.response()?.errorBody()?.string())
        return delServidor ?: when (e.code()) {
            400 -> "Datos inválidos"
            409 -> "El RUT o el email ya están registrados"
            else -> "Error del servidor (${e.code()})"
        }
    }

    private fun leerMensaje(cuerpo: String?): String? =
        try {
            val json = Gson().fromJson(cuerpo, Map::class.java)
            (json?.get("message") ?: json?.get("mensaje"))?.toString()
        } catch (e: Exception) {
            null
        }
}
