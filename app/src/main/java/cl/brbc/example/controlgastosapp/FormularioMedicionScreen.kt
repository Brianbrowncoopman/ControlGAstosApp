package cl.brbc.example.controlgastosapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cl.brbc.example.controlgastosapp.data.Medicion
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun FormularioMedicionSceen(
    viewModel: MedicionViewModel,
    onVolver: () -> Unit
) {
    var valor by remember { mutableStateOf("") }
    // Mantenemos la fecha como String para el TextField
    var fechaTexto by remember { mutableStateOf(LocalDate.now().toString()) }

    val opcionesTipo = listOf(
        stringResource(R.string.type_water),
        stringResource(R.string.type_light),
        stringResource(R.string.type_gas)
    )
    val (tipoSeleccionado, onTipoSelected) = remember { mutableStateOf(opcionesTipo[0]) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.title_form),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = valor,
            onValueChange = { valor = it },
            label = { Text(stringResource(R.string.label_value)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = fechaTexto,
            onValueChange = { fechaTexto = it },
            label = { Text(stringResource(R.string.label_date)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Esta es la línea que daba error (ahora debe existir en strings.xml)
        Text(
            text = stringResource(R.string.label_meter),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Column(Modifier.selectableGroup().fillMaxWidth()) {
            opcionesTipo.forEach { texto ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .selectable(
                            selected = (texto == tipoSeleccionado),
                            onClick = { onTipoSelected(texto) },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (texto == tipoSeleccionado),
                        onClick = null
                    )
                    Text(
                        text = texto,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                // SOLUCIÓN AL TIPO DE DATO: Convertimos String a LocalDate
                val fechaParsed = try {
                    LocalDate.parse(fechaTexto)
                } catch (e: Exception) {
                    LocalDate.now()
                }

                val nuevaMedicion = Medicion(
                    tipo = tipoSeleccionado,
                    valor = valor.toIntOrNull() ?: 0,
                    fecha = fechaParsed // Ahora sí es LocalDate
                )
                viewModel.insertarMedicion(nuevaMedicion)
                onVolver()
            },
            modifier = Modifier
                .width(200.dp)
                .height(48.dp),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Text(stringResource(R.string.btn_save))
        }
    }
}