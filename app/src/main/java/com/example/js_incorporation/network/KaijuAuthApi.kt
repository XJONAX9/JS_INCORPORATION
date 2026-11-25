package com.example.js_incorporation.network

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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

    @GET("api/v1/kaiju/auth/usuarios")
    suspend fun listarUsuarios(): List<UsuarioKaijuResponse>

    @DELETE("api/v1/kaiju/auth/usuarios/{id}")
    suspend fun eliminarUsuario(@Path("id") id: Long)
}
