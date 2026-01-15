package cl.brbc.example.controlgastosapp

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cl.brbc.example.controlgastosapp.MedicionViewModel


@Composable
fun ListaMedicionesScreen(
    viewModel: MedicionViewModel,
    onNavegarAFormulario: () -> Unit
) {
    val listaMediciones by viewModel.todasLAsMediciones.collectAsState()

    Scaffold(
        topBar = { Text("Historial de Mediciones")},
        floatingActionButton = {
            Button(onClick = onNavegarAFormulario) {
                Text("+")
            }
        }
    ) {
        padding ->
        LazyColumn(modifier = Modifier.padding(padding)){
            items(listaMediciones){medicion ->
                Text(text = "${medicion.tipo}: ${medicion.valor} - ${medicion.fecha}")
            }
        }
    }
}

