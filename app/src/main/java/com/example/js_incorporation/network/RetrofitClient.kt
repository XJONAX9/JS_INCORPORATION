package com.example.js_incorporation.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"
    private const val FAKESTORE_URL = "https://fakestoreapi.com/"

    private const val AUTH_URL = BASE_URL

    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    init {
        logger.redactHeader("Authorization")
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .addInterceptor(logger)
        .build()

    private val authClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .build()

    val productoApi: ProductoApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(ProductoApi::class.java)
    }

    val movimientoApi: MovimientoApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(MovimientoApi::class.java)
    }

    val kaijuAuthApi: KaijuAuthApi by lazy {
        Retrofit.Builder()
            .baseUrl(AUTH_URL)
            .client(authClient)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(KaijuAuthApi::class.java)
    }

    val configuracionApi: ConfiguracionApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(authClient)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(ConfiguracionApi::class.java)
    }

    val fakeStoreApi: FakeStoreApi by lazy {
        Retrofit.Builder()
            .baseUrl(FAKESTORE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(FakeStoreApi::class.java)
    }
}
