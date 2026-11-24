package com.example.js_incorporation.network

import retrofit2.http.Body
import retrofit2.http.POST


data class RegistroKaijuRequest(
    val nombreCompleto: String,
    val rut: String,
    val email: String,
    val password: String,
    val rol: String
)

data class LoginKaijuRequest(
    val email: String,
    val password: String
)

data class UsuarioKaijuResponse(
    val id: Long,
    val rut: String,
    val nombreCompleto: String,
    val email: String,
    val rol: String
)

data class LoginKaijuResponse(
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Long,
    val usuario: UsuarioKaijuResponse
)

interface KaijuAuthApi {

    @POST("api/v1/kaiju/auth/registro")
    suspend fun registrar(@Body request: RegistroKaijuRequest): UsuarioKaijuResponse

    @POST("api/v1/kaiju/auth/login")
    suspend fun login(@Body request: LoginKaijuRequest): LoginKaijuResponse
}
