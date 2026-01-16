package cl.brbc.example.controlgastosapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Indico que esta función requiere API 26 debido a que las pantallas que contiene manejan fechas modernas
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(viewModel: MedicionViewModel){
    // Inicializo el controlador de navegación que gestionará la pila de pantallas (backstack)
    val navController = rememberNavController()

    // Configuro el NavHost, definiendo que la pantalla de inicio será la "lista"
    NavHost(navController = navController, startDestination = "lista") {

        // Defino la ruta para la Pantalla 1: Lista de mediciones
        composable("lista"){
            ListaMedicionesScreen(
                viewModel = viewModel, // Le paso el ViewModel para que cargue los datos
                onNavegarAFormulario = {
                    // Cuando el usuario presiona el botón "+", le ordeno al controlador ir al formulario
                    navController.navigate("formulario")
                }
            )
        }

        // Defino la ruta para la Pantalla 2: Formulario de registro
        composable("formulario"){
            FormularioMedicionSceen(
                viewModel = viewModel, // Le paso el mismo ViewModel para que pueda guardar la nueva medición
                onVolver = {
                    // Cuando el usuario guarda o cancela, quito esta pantalla de la pila para volver atrás
                    navController.popBackStack()
                }
            )
        }
    }
}