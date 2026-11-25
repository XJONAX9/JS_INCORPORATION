package com.example.js_incorporation.ui.utils

import android.util.Patterns

fun validarEmail(email: String): String? = when {
    email.isBlank() -> "El email es obligatorio"
    !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Formato de email inválido"
    else -> null
}

fun validarPassword(password: String): String? = when {
    password.isBlank() -> "La contraseña es obligatoria"
    password.length < 6 -> "Debe tener al menos 6 caracteres"
    else -> null
}

fun validarNombre(nombre: String): String? = when {
    nombre.isBlank() -> "El nombre es obligatorio"
    !nombre.trim().contains(" ") -> "Ingresa nombre y apellido"
    else -> null
}

fun validarConfirmacion(password: String, confirmacion: String): String? = when {
    confirmacion.isBlank() -> "Repite la contraseña"
    confirmacion != password -> "Las contraseñas no coinciden"
    else -> null
}

private fun limpiarRut(rut: String): String =
    rut.replace(".", "").replace("-", "").trim().uppercase()

private fun calcularDigitoVerificador(cuerpo: String): Char {
    var suma = 0
    var multiplicador = 2
    for (digito in cuerpo.reversed()) {
        suma += digito.digitToInt() * multiplicador
        multiplicador = if (multiplicador == 7) 2 else multiplicador + 1
    }
    return when (val resultado = 11 - (suma % 11)) {
        11 -> '0'
        10 -> 'K'
        else -> resultado.digitToChar()
    }
}

fun validarRut(rut: String): String? {
    val limpio = limpiarRut(rut)
    if (limpio.isBlank()) return "El RUT es obligatorio"

    val cuerpo = limpio.dropLast(1)
    val dv = limpio.last()

    if (cuerpo.length !in 7..8 || !cuerpo.all { it.isDigit() }) {
        return "Formato inválido (ej: 12.345.678-5)"
    }
    if (calcularDigitoVerificador(cuerpo) != dv) {
        return "RUT inválido: el dígito verificador no coincide"
    }
    return null
}
