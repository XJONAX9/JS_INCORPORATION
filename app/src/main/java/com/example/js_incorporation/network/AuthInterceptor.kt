package com.example.js_incorporation.network

import com.example.js_incorporation.repository.SesionKaiju
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = SesionKaiju.token
        val peticion = if (token != null) {
            chain.request().newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } else {
            chain.request()
        }

        val respuesta = chain.proceed(peticion)

        if (respuesta.code == 401 && token != null) {
            SesionKaiju.expirar()
        }
        return respuesta
    }
}
