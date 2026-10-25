package com.example.js_incorporation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.Alignment
import com.example.js_incorporation.viewmodels.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovimientoScreen(
    productoViewModel: ProductoViewModel,
    alVolver: () -> Unit
) {
    val productos by productoViewModel.productos.collectAsState()

    var productoSeleccionado by remember { mutableStateOf("") }
    var cantidadTexto by remember { mutableStateOf("") }
    var esEntrada by remember { mutableStateOf(true) }
    var menuAbierto by remember { mutableStateOf(false) }
    var mensaje by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Movimientos de inventario")

        ExposedDropdownMenuBox(
            expanded = menuAbierto,
            onExpandedChange = { menuAbierto = !menuAbierto }
        ) {
            OutlinedTextField(
                value = productos.find { it.codigo == productoSeleccionado }
                    ?.let { "${it.codigo} - ${it.nombre}" } ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text("Seleccionar producto") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = menuAbierto
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = menuAbierto,
                onDismissRequest = { menuAbierto = false }
            ) {
                productos.forEach { producto ->
                    DropdownMenuItem(
                        text = {
                            Text("${producto.codigo} - ${producto.nombre}")
                        },
                        onClick = {
                            productoSeleccionado = producto.codigo
                            menuAbierto = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = cantidadTexto,
            onValueChange = { cantidadTexto = it },
            label = { Text("Cantidad") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Tipo de movimiento")

        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            RadioButton(
                selected = esEntrada,
                onClick = { esEntrada = true }
            )
            Text("Entrada")

            RadioButton(
                selected = !esEntrada,
                onClick = { esEntrada = false }
            )
            Text("Salida")
        }

        Button(
            onClick = {
                val cantidad = cantidadTexto.toIntOrNull()
                val producto = productos.find {
                    it.codigo == productoSeleccionado
                }

                mensaje = when {
                    producto == null -> "Selecciona un producto"
                    cantidad == null || cantidad <= 0 ->
                        "Ingresa una cantidad válida"
                    !esEntrada && cantidad > producto.stock ->
                        "No hay suficiente stock para esa salida"
                    else -> {
                        productoViewModel.registrarMovimiento(
                            codigo = producto.codigo,
                            cantidad = cantidad,
                            esEntrada = esEntrada
                        )
                        cantidadTexto = ""
                        "Movimiento registrado correctamente"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar movimiento")
        }

        if (mensaje.isNotEmpty()) {
            Text(mensaje)
        }

        Button(
            onClick = alVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}

