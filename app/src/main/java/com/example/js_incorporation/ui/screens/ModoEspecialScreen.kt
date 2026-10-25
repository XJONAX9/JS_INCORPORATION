package com.example.js_incorporation.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.js_incorporation.viewmodels.EstadoViewModel

private val AzulOscuro = Color(0xFF101A30)
private val AzulTarjeta = Color(0xFF1D2A42)
private val Blanco = Color(0xFFF4F6FA)

@Composable
fun ModoEspecialScreen(
    modifier: Modifier = Modifier,
    viewModel: EstadoViewModel = viewModel()
) {
    val estado by viewModel.activo.collectAsState()
    val mostrarMensaje by viewModel.mostrarMensaje.collectAsState()

    if (estado == null) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(AzulOscuro),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Blanco)
        }
    } else {
        val estaActivo = estado == true

        val colorAnimado by animateColorAsState(
            targetValue = if (estaActivo) Color(0xFF4CAF50)
            else Color(0xFFBBDEFB),
            animationSpec = tween(durationMillis = 500),
            label = "colorModo"
        )

        val textoBoton = if (estaActivo) {
            "Desactivar modo especial"
        } else {
            "Activar modo especial"
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(AzulOscuro)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Modo especial",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Blanco
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Configura el estado especial de Kaiju Inventario.",
                color = Blanco,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AzulTarjeta
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    Text(
                        text = if (estaActivo) {
                            "Modo especial activado"
                        } else {
                            "Modo especial desactivado"
                        },
                        color = Blanco,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Button(
                        onClick = {
                            viewModel.alternarEstado()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorAnimado
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                    ) {
                        Text(
                            text = textoBoton,
                            color = Color.Black
                        )
                    }

                    AnimatedVisibility(
                        visible = mostrarMensaje
                    ) {
                        Text(
                            text = "Estado guardado exitosamente",
                            color = Color(0xFF81C784),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

