package com.example.js_incorporation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.js_incorporation.viewmodels.ProductoViewModel

@Composable
fun HistorialMovimientosScreen(
    productoViewModel: ProductoViewModel,
    alVolver: () -> Unit
) {
    val movimientos by productoViewModel.movimientos.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Historial de movimientos",
            style = MaterialTheme.typography.headlineSmall
        )

        Button(
            onClick = alVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }

        if (movimientos.isEmpty()) {
            Text("Todavía no hay movimientos registrados.")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(movimientos) { movimiento ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF1D2A42)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = movimiento.nombreProducto,
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = "Código: ${movimiento.codigoProducto}",
                                color = Color.White
                            )

                            Text(
                                text = "Tipo: ${movimiento.tipo}",
                                color = if (movimiento.tipo == "Entrada") {
                                    Color(0xFF81C784)
                                } else {
                                    Color(0xFFFF8A80)
                                }
                            )

                            Text(
                                text = "Cantidad: ${movimiento.cantidad}",
                                color = Color.White
                            )

                            Text(
                                text = "Fecha: ${movimiento.fecha}",
                                color = Color.White
                            )

                            Text(
                                text = "Stock resultante: ${movimiento.stockResultante}",
                                color = Color.White
                            )

                            if (movimiento.stockResultante <= movimiento.stockMinimo) {
                                Text(
                                    text = "¡ALERTA: STOCK BAJO!",
                                    color = Color(0xFFFF8A80),
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

