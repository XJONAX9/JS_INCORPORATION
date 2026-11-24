# Kaiju Inventario - Aplicación Android

## Descripción

Kaiju Inventario es una aplicación móvil desarrollada para la asignatura DSY1105. Su objetivo es facilitar la administración de productos, el control de inventario y el registro de movimientos de stock mediante una interfaz móvil moderna desarrollada en Android.

La aplicación se encuentra conectada a un backend desarrollado en Spring Boot mediante Retrofit, permitiendo la comunicación en tiempo real con servicios REST.

---

## Integrantes

- Jonathan Pozo 
- Sebastian Fica

---

## Tecnologías Utilizadas

### Android

- Kotlin
- Jetpack Compose
- Navigation Compose
- ViewModel
- Retrofit
- Room Database
- DataStore
- Coil

### Consumo de APIs

- API Propia (Spring Boot)
- Fake Store API

---

## Funcionalidades

### Gestión de Usuarios

- Registro de usuario
- Inicio de sesión
- Validación de credenciales
- Cierre de sesión
- Perfil de usuario

### Gestión de Inventario

- Crear productos
- Editar productos
- Eliminar productos
- Consultar inventario
- Visualizar stock mínimo

### Gestión de Movimientos

- Registrar entradas
- Registrar salidas
- Actualización automática de stock
- Historial de movimientos

### API Externa

- Consulta de productos externos obtenidos desde Fake Store API.
- Visualización de imágenes.
- Visualización de categorías.
- Visualización de precios.

---

## Arquitectura

Android App
↓
Retrofit
↓
Spring Boot API
↓
Base de Datos

---

## API Propia

Backend desarrollado en Spring Boot para:

- Productos
- Movimientos de Inventario

---

## API Externa

Fake Store API

https://fakestoreapi.com

---

## Funcionalidades Implementadas con Retrofit

### Productos

- GET /productos
- POST /productos
- PUT /productos/{codigo}
- DELETE /productos/{codigo}

### Movimientos

- GET /movimientos
- POST /movimientos

---

## Requisitos

- Android 9.0 o superior
- Android Studio
- Backend Spring Boot ejecutándose
- Conexión a internet

---

## Ejecución

1. Clonar repositorio.
2. Abrir proyecto en Android Studio.
3. Iniciar backend Spring Boot.
4. Ejecutar aplicación.
5. Iniciar sesión y utilizar funcionalidades.

---

## Evidencias

- CRUD conectado mediante API REST.
- Consumo de API externa.
- Integración Android + Spring Boot.
- Pruebas unitarias desarrolladas para validación del backend.
