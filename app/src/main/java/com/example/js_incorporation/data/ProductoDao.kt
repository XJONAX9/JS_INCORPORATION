package com.example.js_incorporation.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.js_incorporation.model.Producto

@Dao
interface ProductoDao {
    @Query("SELECT * FROM productos")
    suspend fun obtenerTodos(): List<Producto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(producto: Producto)

    @Query("DELETE FROM productos")
    suspend fun eliminarTodos()
}
