package com.example.js_incorporation.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import com.example.js_incorporation.model.MovimientoDto

data class MovimientoRequest(
    val tipoMovimiento: String,
    val cantidad: Int,
    val fecha: String,
    val codigoProducto: String
)

interface MovimientoApi {
    @GET("movimientos")
    suspend fun obtenerMovimientos(): List<MovimientoDto>

    @POST("movimientos")
    suspend fun registrarMovimiento(
        @Body movimiento: MovimientoRequest
    ): MovimientoRequest
}
