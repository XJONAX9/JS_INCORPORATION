# Kaiju Inventario - Aplicación Android

## Descripción

Kaiju Inventario es una aplicación móvil desarrollada para la asignatura DSY1105. Su objetivo es facilitar la administración de productos, el control de inventario y el registro de movimientos de stock mediante una interfaz móvil moderna desarrollada en Android.

La aplicación se encuentra conectada a un backend desarrollado en Spring Boot mediante Retrofit, permitiendo la comunicación en tiempo real con servicios REST. El inicio de sesión y el registro de usuarios también se realizan contra este backend.

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

- Registro de usuario (nombre completo, RUT, correo, contraseña y confirmación de contraseña)
- Inicio de sesión validado contra el backend
- Validación de formato de correo, contraseña (mínimo 6 caracteres) y RUT chileno (con dígito verificador)
- Roles de usuario: Admin y Bodega (ver tabla de permisos)
- La primera cuenta registrada en el sistema queda como Admin; las demás quedan como Bodega
- Sesión con token JWT, guardado mientras la aplicación está abierta
- Mensajes de error entregados por el servidor (credenciales incorrectas, RUT o correo ya registrados, sin conexión)
- Cierre de sesión (borra el usuario y el token)
- Perfil de usuario (nombre, correo, RUT y rol)
- Si la sesión vence o la cuenta es eliminada, la aplicación vuelve al inicio de sesión
- Administración de usuarios (solo Admin): ver cuentas registradas, eliminarlas y cambiar su rol

### Roles y permisos

| Acción | Admin | Bodega |
|--------|:-----:|:------:|
| Ver todos los productos y el stock | Sí | Sí |
| Agregar productos | Sí | No |
| Editar productos | Sí | No |
| Eliminar productos | Sí | Sí |
| Registrar entradas y salidas | Sí | Sí |
| Ver movimientos de inventario | Sí | Sí |
| Ver quién agregó cada producto y quién registró cada movimiento | Sí | No |
| Ver usuarios registrados | Sí | No |
| Eliminar cuentas y cambiar roles | Sí | No |
| Cambiar configuraciones (Modo especial) | Sí | No |

Los permisos se aplican en la aplicación (se ocultan las opciones) y también en el backend (que rechaza las acciones no permitidas).

### Configuración global

El Modo especial se guarda en el servidor: cuando un Admin lo cambia, el cambio se aplica a todos los usuarios. Los usuarios Bodega pueden ver el estado, pero no modificarlo. DataStore guarda una copia local para cuando no hay conexión.

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
ViewModel
↓
Repository (autenticación) / Retrofit
↓
Spring Boot API
↓
Base de Datos

---

## API Propia

Backend desarrollado en Spring Boot para:

- Autenticación de usuarios (registro e inicio de sesión)
- Roles y permisos
- Configuración global
- Productos
- Movimientos de Inventario

---

## API Externa

Fake Store API

https://fakestoreapi.com

---

## Funcionalidades Implementadas con Retrofit

### Autenticación y usuarios

- POST /api/v1/kaiju/auth/registro
- POST /api/v1/kaiju/auth/login
- GET /api/v1/kaiju/auth/usuarios (Admin)
- DELETE /api/v1/kaiju/auth/usuarios/{id} (Admin)
- PUT /api/v1/kaiju/auth/usuarios/{id}/rol (Admin)

### Configuración

- GET /api/v1/kaiju/configuracion
- PUT /api/v1/kaiju/configuracion (Admin)

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

## Configuración de la conexión al backend

La dirección del backend se define en `network/RetrofitClient.kt`:

```kotlin
private const val BASE_URL = "http://10.0.2.2:8080/"
```

- `10.0.2.2` funciona **solo en el emulador** de Android Studio (apunta al computador que lo ejecuta).
- Si se usa un celular físico, reemplazar por la IP del computador en la red local (por ejemplo `http://192.168.1.50:8080/`).
- Las llamadas de autenticación usan la misma dirección (`AUTH_URL = BASE_URL`).
- Después de iniciar sesión, todas las llamadas envían el token en el encabezado `Authorization: Bearer <token>`.

---

## Ejecución

1. Clonar repositorio.
2. Abrir proyecto en Android Studio.
3. Iniciar backend Spring Boot (`mvn spring-boot:run`).
4. Ejecutar aplicación.
5. Registrar un usuario e iniciar sesión.
6. Utilizar funcionalidades.

---

## Evidencias

- Registro e inicio de sesión conectados mediante API REST.
- CRUD conectado mediante API REST.
- Consumo de API externa.
- Integración Android + Spring Boot.
- Pruebas unitarias desarrolladas para validación del backend.
