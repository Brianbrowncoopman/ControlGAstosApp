package cl.brbc.example.controlgastosapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
/*import cl.brbc.example.controlgastosapp.data.MedicionApp*/
import cl.brbc.example.controlgastosapp.AppNavigation
import cl.brbc.example.controlgastosapp.data.MedicionApp

// Indico que esta actividad principal requiere Android Oreo (API 26) para funcionar
@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : ComponentActivity() {

    // Este es el primer método que se ejecuta al abrir la aplicación
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Accedo a la instancia global de mi aplicación (MedicionApp) para obtener los datos
        val app = application as MedicionApp

        // Obtengo el DAO (objeto de acceso a datos) que configuré en la base de datos
        val dao = app.medicionDao

        // Inicializo el ViewModel y le entrego el DAO para que pueda realizar las consultas SQL
        val viewModel = MedicionViewModel(dao)

        // Establezco el contenido visual de la actividad
        setContent {
            // Lanzo el sistema de navegación que creamos, pasándole el ViewModel configurado
            AppNavigation(viewModel)
        }
    }
}

