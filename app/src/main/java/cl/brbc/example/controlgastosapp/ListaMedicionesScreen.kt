package cl.brbc.example.controlgastosapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cl.brbc.example.controlgastosapp.data.Medicion
import cl.brbc.example.controlgastosapp.R
import cl.brbc.example.controlgastosapp.ui.theme.FooterApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaMedicionesScreen(
    viewModel: MedicionViewModel, // Recibo el ViewModel para observar la lista de datos
    onNavegarAFormulario: () -> Unit // Defino la acción para ir a la pantalla de registro
) {
    // Observo la lista de mediciones desde el ViewModel y la convierto en un estado de Compose
    val listaMediciones by viewModel.todasLAsMediciones.collectAsState()

    // Configuro la estructura básica de la pantalla (barra superior y botón flotante)
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.title_list)) }
            )
        },
        floatingActionButton = {
            // Configuro el botón "+" para navegar al formulario
            FloatingActionButton(onClick = onNavegarAFormulario) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },
        bottomBar = {
            FooterApp()
        }
    ) { padding ->
        // Utilizo una LazyColumn para renderizar solo los elementos visibles en pantalla (eficiencia)
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Recorro la lista de mediciones y creo un ítem visual para cada una
            items(listaMediciones) { medicion ->
                MedicionItem(
                    medicion = medicion,
                    onBorrar = { viewModel.eliminarMedicion(it) } // Ejecuto la eliminación desde el ViewModel
                )
                // Dibujo una línea divisoria sutil entre cada tarjeta
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp)
            }
        }
    }
}

@Composable
fun MedicionItem(
    medicion: Medicion,
    onBorrar: (Medicion) -> Unit
) {
    // Determino el color de fondo de la tarjeta según el tipo de servicio (Lógica condicional)
    val backgroundColor = when (medicion.tipo.lowercase()) {
        "agua", "water" -> androidx.compose.ui.graphics.Color(0xFFE3F2FD) // Azul pálido para agua
        "luz", "electricity" -> androidx.compose.ui.graphics.Color(0xFFFFFDE7) // Amarillo pálido para electricidad
        "gas" -> androidx.compose.ui.graphics.Color(0xFFFFEBEE) // Rojo pálido para gas
        else -> MaterialTheme.colorScheme.surface
    }

    // Creo una tarjeta (Card) para darle relieve y el color de fondo dinámico al ítem
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Selecciono el icono correspondiente según el tipo de medición
            val iconRes = when (medicion.tipo.lowercase()) {
                "agua", "water" -> R.drawable.ic_water
                "luz", "electricity" -> R.drawable.ic_light
                else -> R.drawable.ic_gas
            }

            // Muestro el icono descriptivo del servicio
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Organizo el nombre del servicio y el valor consumido en una columna central
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = medicion.tipo.uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                // Formateo el valor numérico (ej: 1.000) usando mi función auxiliar
                Text(
                    text = formatValor(medicion.valor),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // Organizo la fecha y el botón de borrar horizontalmente al final de la tarjeta
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = medicion.fecha.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold, // Estilo grueso para resaltar la fecha
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(end=4.dp)
                )
                // Botón de acción para eliminar el registro
                IconButton(onClick = { onBorrar(medicion) }) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Delete,
                        contentDescription = "Borrar",
                        tint = androidx.compose.ui.graphics.Color(0xFFB71C1C) // Uso rojo oscuro para indicar peligro/borrado
                    )
                }
            }
        }
    }
}

// Función auxiliar para formatear los números con separadores de miles de Chile
fun formatValor(valor: Int): String {
    return java.text.NumberFormat.getInstance(java.util.Locale("es", "CL")).format(valor)
}