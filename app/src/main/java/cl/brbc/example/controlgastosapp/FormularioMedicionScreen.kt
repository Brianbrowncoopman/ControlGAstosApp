package cl.brbc.example.controlgastosapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.brbc.example.controlgastosapp.data.Medicion
import cl.brbc.example.controlgastosapp.MedicionViewModel
import java.time.LocalDate

@Composable
fun FormularioMedicionSceen(
    viewModel: MedicionViewModel,
    onVolver: () -> Unit
) {
    var valor by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Text(text = "Registrar Nueva Medicion", style = MaterialTheme.typography.headlineMedium)
        // campo para el valor
        OutlinedTextField(
            value = valor,
            onValueChange = {valor = it},
            label = {Text("Valor del emdidor")},
            modifier = Modifier.fillMaxWidth()
        )

        //campo para el Tipo
        OutlinedTextField(
            value = tipo,
            onValueChange = {tipo = it},
            label = {Text("Tipo de Medidor")},
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val nuevaMedicion = Medicion(
                    tipo = tipo,
                    valor = valor.toIntOrNull() ?: 0,
                    fecha = LocalDate.now()
                )
                viewModel.insertarMedicion(nuevaMedicion)
                onVolver()
            },
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Guardar registro")
        }
    }
}