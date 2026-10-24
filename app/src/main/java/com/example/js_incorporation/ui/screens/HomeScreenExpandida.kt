
package com.example.js_incorporation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.js_incorporation.R
import com.example.js_incorporation.ui.theme.JS_INCORPORATIONTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenExpandida() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Kaiju Inventario") }
            )
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(32.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    text = "Panel de inventario",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(
                    text = "Administra los productos de Kaiju Inventario."
                )

                Button(onClick = { }) {
                    Text(text = "Ver inventario")
                }

                Button(onClick = { }) {
                    Text(text = "Registrar producto")
                }
            }

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo Kaiju Inventario",
                modifier = Modifier
                    .weight(1f)
                    .height(300.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 900, heightDp = 800)
@Composable
fun HomeScreenExpandidaPreview() {
    JS_INCORPORATIONTheme {
        HomeScreenExpandida()
    }
}

