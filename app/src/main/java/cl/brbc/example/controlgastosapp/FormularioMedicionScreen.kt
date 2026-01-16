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
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

// Indico que esta pantalla requiere API 26 para manejar fechas modernas
@RequiresApi(Build.VERSION_CODES.O)
// Activo las funciones experimentales de Material 3 para usar el DatePicker
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioMedicionSceen(
    viewModel: MedicionViewModel, // Recibo el ViewModel para guardar los datos
    onVolver: () -> Unit // Defino la acción para regresar a la lista
) {
    // Uso 'remember' para controlar si el calendario debe mostrarse o no
    var mostrarCalendario by remember { mutableStateOf(false) }
    // Creo el estado que manejará la fecha seleccionada en el componente DatePicker
    val datePickerState = rememberDatePickerState()

    // Declaro los estados para capturar el valor escrito y la fecha en formato texto
    var valor by remember { mutableStateOf("") }
    var fechaTexto by remember { mutableStateOf(LocalDate.now().toString()) }

    // Cargo las opciones del medidor desde los recursos de strings para que sean traducibles
    val opcionesTipo = listOf(
        stringResource(R.string.type_water),
        stringResource(R.string.type_light),
        stringResource(R.string.type_gas)
    )
    // Gestiono cuál opción del RadioButton está marcada actualmente
    val (tipoSeleccionado, onTipoSelected) = remember { mutableStateOf(opcionesTipo[0]) }

    // Organizo todos los elementos de forma vertical y centrada
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Muestro el título del formulario con un estilo llamativo
        Text(
            text = stringResource(R.string.title_form),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Configuro el campo para ingresar el valor de la medición
        OutlinedTextField(
            value = valor,
            onValueChange = { input: String ->
                // Aplico mi validación: solo permito números y un máximo de 7 dígitos
                if (input.all { it.isDigit() } && input.length <= 7) {
                    valor = input
                }
            },
            label = { Text(stringResource(R.string.label_value)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            // Obligo a que se abra el teclado numérico del teléfono
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Configuro el campo de fecha como solo lectura para que el usuario no escriba manualmente
        OutlinedTextField(
            value = fechaTexto,
            onValueChange = { },
            readOnly = true,
            label = { Text(stringResource(R.string.label_date)) },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { mostrarCalendario = true }, // Al hacer clic, activo el calendario
            enabled = false, // Lo deshabilito para que mantenga el estilo visual de "solo lectura"
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledBorderColor = MaterialTheme.colorScheme.outline,
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

        // Si el estado mostrarCalendario es verdadero, dibujo el diálogo del calendario
        if (mostrarCalendario) {
            DatePickerDialog(
                onDismissRequest = { mostrarCalendario = false },
                confirmButton = {
                    TextButton(onClick = {
                        // Al confirmar, tomo los milisegundos y los convierto a LocalDate en UTC
                        datePickerState.selectedDateMillis?.let { millis ->
                            val fechaSeleccionada = java.time.Instant.ofEpochMilli(millis)
                                .atZone(java.time.ZoneOffset.UTC) // Evito el error de que me reste un día
                                .toLocalDate()
                            fechaTexto = fechaSeleccionada.toString()
                        }
                        mostrarCalendario = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarCalendario = false }) {
                        Text("Cancelar")
                    }
                }
            ) {
                // El componente visual del calendario dentro del diálogo
                DatePicker(state = datePickerState)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Etiqueta para la sección de selección de tipo de medidor
        Text(
            text = stringResource(R.string.label_meter),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.SemiBold
        )

        // Creo el grupo de RadioButtons para Agua, Luz y Gas
        Column(Modifier.selectableGroup().fillMaxWidth()) {
            opcionesTipo.forEach { texto ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .selectable(
                            selected = (texto == tipoSeleccionado),
                            onClick = { onTipoSelected(texto) }, // Actualizo la opción elegida
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (texto == tipoSeleccionado),
                        onClick = null // El clic lo maneja la Row completa
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

        // Botón final para procesar y guardar los datos
        Button(
            onClick = {
                // Intento convertir el texto de la fecha a un objeto LocalDate
                val fechaParsed = try {
                    LocalDate.parse(fechaTexto)
                } catch (e: Exception) {
                    LocalDate.now()
                }

                // Creo el objeto Medicion con los datos del formulario
                val nuevaMedicion = Medicion(
                    tipo = tipoSeleccionado,
                    valor = valor.toIntOrNull() ?: 0,
                    fecha = fechaParsed
                )
                // Envío la nueva medición al ViewModel para que la inserte en Room
                viewModel.insertarMedicion(nuevaMedicion)
                // Ejecuto la navegación de vuelta a la lista
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