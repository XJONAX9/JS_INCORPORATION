
package com.example.js_incorporation.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.js_incorporation.model.Producto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject

private val Context.productosDataStore by preferencesDataStore(
    name = "productos_inventario"
)

class ProductoDataStore(private val context: Context) {

    private val productosKey = stringPreferencesKey("lista_productos")

    fun obtenerProductos(): Flow<List<Producto>?> {
        return context.productosDataStore.data.map { preferencias ->
            val jsonGuardado = preferencias[productosKey]

            if (jsonGuardado == null) {
                null
            } else {
                try {
                    val jsonArray = JSONArray(jsonGuardado)
                    val lista = mutableListOf<Producto>()

                    for (i in 0 until jsonArray.length()) {
                        val item = jsonArray.getJSONObject(i)

                        lista.add(
                            Producto(
                                codigo = item.getString("codigo"),
                                nombre = item.getString("nombre"),
                                categoria = item.getString("categoria"),
                                stock = item.getInt("stock"),
                                stockMinimo = item.getInt("stockMinimo")
                            )
                        )
                    }

                    lista
                } catch (e: Exception) {
                    null
                }
            }
        }
    }

    suspend fun guardarProductos(productos: List<Producto>) {
        val jsonArray = JSONArray()

        productos.forEach { producto ->
            val item = JSONObject().apply {
                put("codigo", producto.codigo)
                put("nombre", producto.nombre)
                put("categoria", producto.categoria)
                put("stock", producto.stock)
                put("stockMinimo", producto.stockMinimo)
            }

            jsonArray.put(item)
        }

        context.productosDataStore.edit { preferencias ->
            preferencias[productosKey] = jsonArray.toString()
        }
    }
}
