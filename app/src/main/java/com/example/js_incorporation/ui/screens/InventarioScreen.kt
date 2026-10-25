
package com.example.js_incorporation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.js_incorporation.navigation.Screen
import com.example.js_incorporation.viewmodels.MainViewModel
import com.example.js_incorporation.viewmodels.ProductoViewModel

data class Producto(
    val codigo: String,
    val nombre: String,
    val categoria: String,
    val stock: Int,
    val stockMinimo: Int
)

private val AzulOscuro = Color(0xFF101A30)
private val AzulTarjeta = Color(0xFF1D2A42)
private val Blanco = Color(0xFFF4F6FA)

@Composable
fun InventarioScreen(
    viewModel: MainViewModel,
    productoViewModel: ProductoViewModel,
    alRegistrar: () -> Unit
) {
    val productos by productoViewModel.productos.collectAsState()

    var productoAEliminar by remember { mutableStateOf<Producto?>(null) }
    var productoAEditar by remember { mutableStateOf<Producto?>(null) }

    var nombreEditar by remember { mutableStateOf("") }
    var categoriaEditar by remember { mutableStateOf("") }
    var stockEditar by remember { mutableStateOf("") }
    var minimoEditar by remember { mutableStateOf("") }
    var mensajeEditar by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AzulOscuro)
            .padding(16.dp)
    ) {
        Text(
            text = "Kaiju Inventario",
            color = Blanco,
            fontSize = 25.sp,
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Productos registrados: ${productos.size}",
            color = Color.LightGray,
            modifier = Modifier.padding(top = 8.dp, bottom = 12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = alRegistrar,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrar producto")
                }

                Button(
                    onClick = {
                        viewModel.navigateTo(Screen.Movimientos)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Movimientos de inventario")
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(top = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(productos) { producto ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AzulTarjeta
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = producto.nombre,
                            color = Blanco,
                            fontSize = 18.sp
                        )

                        Text(
                            text = "${producto.codigo} · ${producto.categoria}",
                            color = Color.LightGray,
                            fontSize = 13.sp
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Stock: ${producto.stock}",
                                color = Blanco
                            )

                            Text(
                                text = if (producto.stock <= producto.stockMinimo) {
                                    "Stock bajo"
                                } else {
                                    "Disponible"
                                },
                                color = if (producto.stock <= producto.stockMinimo) {
                                    Color(0xFFFF8A80)
                                } else {
                                    Color(0xFF81C784)
                                }
                            )
                        }

                        Text(
                            text = "Stock mínimo: ${producto.stockMinimo}",
                            color = Color.LightGray
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    productoAEditar = producto
                                    nombreEditar = producto.nombre
                                    categoriaEditar = producto.categoria
                                    stockEditar = producto.stock.toString()
                                    minimoEditar = producto.stockMinimo.toString()
                                    mensajeEditar = ""
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Editar")
                            }

                            Button(
                                onClick = {
                                    productoAEliminar = producto
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }

        TextButton(
            onClick = {
                viewModel.navigateTo(Screen.Home)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Volver al inicio",
                color = Blanco
            )
        }
    }

    if (productoAEliminar != null) {
        AlertDialog(
            onDismissRequest = {
                productoAEliminar = null
            },
            title = {
                Text("Eliminar producto")
            },
            text = {
                Text(
                    "¿Estás seguro de que quieres eliminar " +
                            "${productoAEliminar?.nombre}?"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        productoAEliminar?.let { producto ->
                            productoViewModel.eliminarProducto(producto)
                        }
                        productoAEliminar = null
                    }
                ) {
                    Text("Sí, eliminar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        productoAEliminar = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (productoAEditar != null) {
        AlertDialog(
            onDismissRequest = {
                productoAEditar = null
            },
            title = {
                Text("Editar producto")
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Código: ${productoAEditar?.codigo}")

                    OutlinedTextField(
                        value = nombreEditar,
                        onValueChange = { nombreEditar = it },
                        label = { Text("Nombre") },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = categoriaEditar,
                        onValueChange = { categoriaEditar = it },
                        label = { Text("Categoría") },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = stockEditar,
                        onValueChange = { stockEditar = it },
                        label = { Text("Stock disponible") },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = minimoEditar,
                        onValueChange = { minimoEditar = it },
                        label = { Text("Stock mínimo") },
                        singleLine = true
                    )

                    if (mensajeEditar.isNotEmpty()) {
                        Text(
                            text = mensajeEditar,
                            color = Color(0xFFD32F2F)
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val stock = stockEditar.toIntOrNull()
                        val minimo = minimoEditar.toIntOrNull()

                        when {
                            nombreEditar.isBlank() ||
                                    categoriaEditar.isBlank() -> {
                                mensajeEditar = "Completa todos los campos."
                            }

                            stock == null || minimo == null -> {
                                mensajeEditar = "Ingresa cantidades válidas."
                            }

                            stock < 0 || minimo < 0 -> {
                                mensajeEditar =
                                    "Las cantidades no pueden ser negativas."
                            }

                            else -> {
                                productoAEditar?.let { producto ->
                                    productoViewModel.editarProducto(
                                        Producto(
                                            codigo = producto.codigo,
                                            nombre = nombreEditar.trim(),
                                            categoria = categoriaEditar.trim(),
                                            stock = stock,
                                            stockMinimo = minimo
                                        )
                                    )
                                }
                                productoAEditar = null
                            }
                        }
                    }
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        productoAEditar = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}


