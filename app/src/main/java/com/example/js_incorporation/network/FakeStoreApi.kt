package com.example.js_incorporation.network

import com.example.js_incorporation.model.ProductoExterno
import retrofit2.http.GET

interface FakeStoreApi {

    @GET("products")
    suspend fun obtenerProductos():
            List<ProductoExterno>
}