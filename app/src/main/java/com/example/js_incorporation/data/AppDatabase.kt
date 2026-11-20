package com.example.js_incorporation.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.js_incorporation.model.Producto
@Database(
    entities = [Producto::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
}