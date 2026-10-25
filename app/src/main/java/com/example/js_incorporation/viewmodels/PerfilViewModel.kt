
package com.example.js_incorporation.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PerfilViewModel : ViewModel() {

    private val _imagenUri = MutableStateFlow<Uri?>(null)
    val imagenUri: StateFlow<Uri?> = _imagenUri.asStateFlow()

    fun actualizarImagenGaleria(uri: Uri?) {
        _imagenUri.value = uri
    }

    fun actualizarImagenCamara(uri: Uri?) {
        _imagenUri.value = uri
    }
}
