package com.example.js_incorporation.network

import com.example.js_incorporation.model.Producto
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.DELETE
import retrofit2.http.Path
import retrofit2.http.PUT

interface ProductoApi {

    @GET("productos")
    suspend fun obtenerProductos(): List<Producto>

    @POST("productos")
    suspend fun registrarProducto(
        @Body producto: Producto
    ): Producto

    @DELETE("productos/{codigo}")
    suspend fun eliminarProducto(
        @Path("codigo") codigo: String
    )

    @PUT("productos/{codigo}")
    suspend fun editarProducto(
        @Path("codigo") codigo: String,
        @Body producto: Producto
    ): Producto
}