package cl.brbc.example.controlgastosapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cl.brbc.example.controlgastosapp.data.Medicion
import cl.brbc.example.controlgastosapp.ui.theme.FooterApp
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioMedicionSceen(
    viewModel: MedicionViewModel,
    medicionId: Int, // Recibimos el ID
    onVolver: () -> Unit
) {
    var mostrarCalendario by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    var valor by remember { mutableStateOf("") }
    var fechaTexto by remember { mutableStateOf(LocalDate.now().toString()) }

    val opcionesTipo = listOf(
        stringResource(R.string.type_water),
        stringResource(R.string.type_light),
        stringResource(R.string.type_gas)
    )
    var tipoSeleccionado by remember { mutableStateOf(opcionesTipo[0]) }

    // CARGAR DATOS SI ES EDICIÓN
    LaunchedEffect(medicionId) {
        if (medicionId != -1) {
            val medicionExistente = viewModel.obtenerMedicionPorId(medicionId)
            medicionExistente?.let {
                valor = it.valor.toString()
                fechaTexto = it.fecha.toString()
                tipoSeleccionado = it.tipo
            }
        }
    }

    Scaffold(
        bottomBar = { FooterApp() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (medicionId == -1) stringResource(R.string.title_form) else "Editar Registro",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = valor,
                onValueChange = { input ->
                    if (input.all { it.isDigit() } && input.length <= 7) valor = input
                },
                label = { Text(stringResource(R.string.label_value)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = fechaTexto,
                onValueChange = { },
                readOnly = true,
                label = { Text(stringResource(R.string.label_date)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { mostrarCalendario = true },
                enabled = false,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            if (mostrarCalendario) {
                DatePickerDialog(
                    onDismissRequest = { mostrarCalendario = false },
                    confirmButton = {
                        TextButton(onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                val fechaSeleccionada = java.time.Instant.ofEpochMilli(millis)
                                    .atZone(java.time.ZoneOffset.UTC)
                                    .toLocalDate()
                                fechaTexto = fechaSeleccionada.toString()
                            }
                            mostrarCalendario = false
                        }) { Text("OK") }
                    },
                    dismissButton = {
                        TextButton(onClick = { mostrarCalendario = false }) { Text("Cancelar") }
                    }
                ) { DatePicker(state = datePickerState) }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.label_meter),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.SemiBold
            )

            Column(Modifier.selectableGroup().fillMaxWidth()) {
                opcionesTipo.forEach { texto ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .selectable(
                                selected = (texto == tipoSeleccionado),
                                onClick = { tipoSeleccionado = texto },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = (texto == tipoSeleccionado), onClick = null)
                        Text(texto, modifier = Modifier.padding(start = 16.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val fechaParsed = try { LocalDate.parse(fechaTexto) } catch (e: Exception) { LocalDate.now() }

                    if (medicionId == -1) {
                        viewModel.insertarMedicion(Medicion(
                            tipo = tipoSeleccionado,
                            valor = valor.toIntOrNull() ?: 0,
                            fecha = fechaParsed
                        ))
                    } else {
                        viewModel.actualizarMedicion(Medicion(
                            id = medicionId,
                            tipo = tipoSeleccionado,
                            valor = valor.toIntOrNull() ?: 0,
                            fecha = fechaParsed
                        ))
                    }
                    onVolver()
                },
                modifier = Modifier.width(200.dp).height(48.dp),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Text(if (medicionId == -1) stringResource(R.string.btn_save) else "Actualizar")
            }
        }
    }
}