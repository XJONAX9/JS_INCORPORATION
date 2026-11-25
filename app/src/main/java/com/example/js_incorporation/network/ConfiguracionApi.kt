package com.example.js_incorporation.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

data class ConfiguracionDto(
    val modoEspecial: Boolean
)

interface ConfiguracionApi {
    @GET("api/v1/kaiju/configuracion")
    suspend fun obtener(): ConfiguracionDto

    @PUT("api/v1/kaiju/configuracion")
    suspend fun actualizar(@Body configuracion: ConfiguracionDto): ConfiguracionDto
}
