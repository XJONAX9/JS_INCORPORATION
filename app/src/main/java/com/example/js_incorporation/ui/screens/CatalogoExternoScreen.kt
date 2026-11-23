package com.example.js_incorporation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.js_incorporation.viewmodels.ProductoExternoViewModel
@Composable
fun CatalogoExternoScreen(
    viewModel: ProductoExternoViewModel = viewModel()
) {
    val productos by viewModel.productos.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(productos) { producto ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    AsyncImage(
                        model = producto.image,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )
                    Text(producto.title)
                    Text(
                        "Categoría: ${producto.category}"
                    )
                    Text(
                        "Precio: $${producto.price}"
                    )
                }
            }
        }
    }
}