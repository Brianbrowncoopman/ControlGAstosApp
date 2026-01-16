plugins {
    // Activo los plugins fundamentales para que el proyecto sea una aplicación Android y use Kotlin
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // Activo KSP para que la generación de código de Room sea mucho más rápida
    id("com.google.devtools.ksp")
}

android {
    // Defino el paquete único que identifica mi aplicación en el mundo
    namespace = "cl.brbc.example.controlgastosapp"
    // Establezco la versión del SDK contra la que compilo el código
    compileSdk = 36

    defaultConfig {
        applicationId = "cl.brbc.example.controlgastosapp"
        // Indico que mi app funciona en teléfonos desde Android 7.0 (API 24) en adelante
        minSdk = 24
        // Apunto a la versión más reciente de Android para aprovechar sus mejoras de seguridad
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            // Configuro la versión final; aquí desactivo la optimización de código para facilitar las pruebas
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Configuro la compatibilidad con Java 11, necesaria para las librerías modernas de Android
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    // Activo Jetpack Compose para poder crear interfaces de usuario modernas y declarativas
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Importo las librerías base de Android y Compose usando el catálogo central (libs.versions.toml)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom)) // Asegura que todas las versiones de Compose coincidan
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Configuro Room: 'runtime' para el funcionamiento, 'ktx' para usar corrutinas y 'ksp' para compilar la base de datos
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    ksp("androidx.room:room-compiler:$room_version")

    // Añado las herramientas de navegación entre pantallas y la integración de ViewModels con Compose
    implementation("androidx.navigation:navigation-compose:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")

    // Cargo las librerías de testeo para asegurar que la app no tenga errores
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}