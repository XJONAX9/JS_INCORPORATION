package com.example.js_incorporation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.js_incorporation.model.Producto
import com.example.js_incorporation.viewmodels.ProductoViewModel

@Composable
fun RegistroProductoScreen(
    productoViewModel: ProductoViewModel,
    alRegistrar: () -> Unit,
    alVolver: () -> Unit
) {
    var codigo by rememberSaveable { mutableStateOf("") }
    var nombre by rememberSaveable { mutableStateOf("") }
    var categoria by rememberSaveable { mutableStateOf("") }
    var stock by rememberSaveable { mutableStateOf("") }
    var stockMinimo by rememberSaveable { mutableStateOf("") }
    var mensaje by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Registrar producto")

        OutlinedTextField(
            value = codigo,
            onValueChange = { codigo = it },
            label = { Text("Código") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del producto") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = categoria,
            onValueChange = { categoria = it },
            label = { Text("Categoría") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = stock,
            onValueChange = { stock = it },
            label = { Text("Cantidad disponible") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = stockMinimo,
            onValueChange = { stockMinimo = it },
            label = { Text("Stock mínimo") },
            modifier = Modifier.fillMaxWidth()
        )

        Text(mensaje)

        Button(
            onClick = {
                val cantidad = stock.toIntOrNull()
                val minimo = stockMinimo.toIntOrNull()

                when {
                    codigo.isBlank() || nombre.isBlank() ||
                            categoria.isBlank() || cantidad == null ||
                            minimo == null -> {
                        mensaje = "Completa todos los campos correctamente."
                    }

                    cantidad < 0 || minimo < 0 -> {
                        mensaje = "Las cantidades no pueden ser negativas."
                    }

                    productoViewModel.productos.value.any {
                        it.codigo.equals(codigo.trim(), ignoreCase = true)
                    } -> {
                        mensaje = "Ese código ya está registrado."
                    }

                    else -> {
                        productoViewModel.registrarProducto(
                            Producto(
                                codigo.trim(),
                                nombre.trim(),
                                categoria.trim(),
                                cantidad,
                                minimo
                            )
                        )
                        alRegistrar()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar producto")
        }

        Button(
            onClick = alVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}