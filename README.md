# 📊 Control de Gastos (Mediciones) - Android App

¡Bienvenido a mi proyecto! He desarrollado esta aplicación nativa para Android para ayudar a los usuarios a llevar un registro histórico y detallado de sus mediciones de servicios básicos como **Agua, Luz y Gas**.

El objetivo principal es permitir un monitoreo constante del consumo mediante una interfaz moderna, intuitiva y visualmente organizada.

---

## 🚀 Funcionalidades Principales

* **Registro de Mediciones**: Formulario dinámico para ingresar el valor del medidor y la fecha exacta.
* **Gestión de Datos**: Implementación completa de un sistema **CRUD** (Crear, Leer, Eliminar) utilizando la base de datos local **Room**.
* **Historial Visual**: Lista dinámica con **colores condicionales** que facilitan la identificación del tipo de servicio:
    * 💧 **Azul**: Agua
    * ⚡ **Amarillo**: Luz
    * 🔥 **Rojo**: Gas
* **Validación Inteligente**: El sistema restringe la entrada de datos a valores numéricos de hasta 7 dígitos para mantener la integridad visual.
* **Calendario Nativo**: Selección de fecha mediante `DatePicker` de Material 3 con corrección de zona horaria (UTC) para evitar desfases de días.

---

## 🛠️ Tecnologías y Arquitectura

He construido esta aplicación utilizando las herramientas más modernas del ecosistema Android:

* **Lenguaje**: [Kotlin](https://kotlinlang.org/) (100%)
* **UI**: [Jetpack Compose](https://developer.android.com/jetpack/compose) para una interfaz declarativa y moderna.
* **Arquitectura**: Seguí el patrón **MVVM (Model-View-ViewModel)** para separar la lógica de negocio de la interfaz de usuario, garantizando un código escalable y fácil de testear.
* **Base de Datos**: [Room Database](https://developer.android.com/training/data-storage/room) para persistencia de datos local segura.
* **Navegación**: [Jetpack Navigation](https://developer.android.com/guide/navigation) para gestionar el flujo entre pantallas de forma fluida.
* **Asincronía**: [Corrutinas de Kotlin](https://kotlinlang.org/docs/coroutines-overview.html) y **Flow** para manejar flujos de datos reactivos en tiempo real.



---

## 📂 Estructura del Proyecto

El código está organizado de forma modular siguiendo las mejores prácticas de la industria:

```text
cl.brbc.example.controlgastosapp/
├── data/                 # Capa de datos (Persistencia)
│   ├── AppDatabase.kt    # Configuración principal de Room
│   ├── Medicion.kt       # Entidad (Definición de la tabla)
│   ├── MedicionDao.kt    # Consultas SQL (Interface de datos)
│   └── LocalDateConverter.kt # Conversor para manejar fechas en SQLite
├── MedicionViewModel.kt  # El cerebro de la app (Estado y Lógica)
├── AppNavigation.kt      # Mapa de rutas de navegación
├── MainActivity.kt       # Punto de entrada y arranque del ViewModel
├── ListaMedicionesScreen.kt # UI: Historial con tarjetas de colores dinámicos
└── FormularioMedicionScreen.kt # UI: Captura de datos, validaciones y DatePicker

## ⚙️ Requisitos de Instalación
Clonar este repositorio.

Abrir el proyecto en Android Studio (Versión Ladybug o superior).

Asegurarse de tener configurado el SDK de Android para el Nivel de API 35/36.

Sincronizar el proyecto con Gradle.

Ejecutar en un dispositivo físico o emulador con un mínimo de Android 7.0 (API 24).


