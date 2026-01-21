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
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "lista") {
        // Pantalla 1: Lista
        composable("lista"){
            ListaMedicionesScreen(
                viewModel = viewModel,
                onNavegarAFormulario = { id -> // se especifica  el tipo para evitar el error de inferencia
                    val medicionId = id ?: -1
                    navController.navigate("formulario/$medicionId")
                }
            )
        }
        // Pantalla 2: Formulario
        composable("formulario/{medicionId}") { backStackEntry ->
            // Extraemos el ID de la ruta
            val idString = backStackEntry.arguments?.getString("medicionId") ?: "-1"
            val id = idString.toInt()

            FormularioMedicionSceen(
                viewModel = viewModel,
                medicionId = id,
                onVolver = {
                    navController.popBackStack()
                }
            )
        }
    }
}