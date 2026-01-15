package cl.brbc.example.controlgastosapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
/*import cl.brbc.example.controlgastosapp.data.MedicionApp*/
import cl.brbc.example.controlgastosapp.AppNavigation
import cl.brbc.example.controlgastosapp.data.MedicionApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as MedicionApp
        val dao = app.medicionDao

        val viewModel = MedicionViewModel(dao)

        setContent {
            AppNavigation(viewModel)
        }
    }
}

