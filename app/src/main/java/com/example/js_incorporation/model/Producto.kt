package com.example.js_incorporation.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "productos")
data class Producto(
    @PrimaryKey
    val codigo: String,
    val nombre: String,
    val categoria: String,
    val stock: Int,
    val stockMinimo: Int,
    val creadoPor: String? = null,
    val creadoPorEmail: String? = null
)
