package cl.brbc.example.controlgastosapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation(viewModel: MedicionViewModel){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "lista") {
        // pantalla 1 Lista de mediciones
        composable("lista"){
            ListaMedicionesScreen(
                viewModel = viewModel,
                onNavegarAFormulario = {
                    navController.navigate("formulario")
                }
            )
        }
        // pantalla 2 Formulario de registro
        composable("formulario"){
            FormularioMedicionSceen(
                viewModel = viewModel,
                onVolver = { navController.popBackStack() }
            )
        }
    }
}