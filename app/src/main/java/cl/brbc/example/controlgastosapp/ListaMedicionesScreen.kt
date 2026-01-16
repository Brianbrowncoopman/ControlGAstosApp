package cl.brbc.example.controlgastosapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaMedicionesScreen(
    viewModel: MedicionViewModel,
    onNavegarAFormulario: () -> Unit
) {
    val listaMediciones by viewModel.todasLAsMediciones.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.title_list)) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavegarAFormulario) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { padding ->
        // Requisito: Utilizar listas dinámicas (LazyColumn)
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(listaMediciones) { medicion ->
                MedicionItem(medicion)
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp)
            }
        }
    }
}

@Composable
fun MedicionItem(medicion: Medicion) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Requisito: Utilizar recursos drawables o íconos
        val iconRes = when (medicion.tipo.lowercase()) {
            "agua" -> R.drawable.ic_water
            "luz" -> R.drawable.ic_light
            else -> R.drawable.ic_gas
        }

        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = medicion.tipo.uppercase(),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        // Valor formateado (ej: 1.900)
        Text(
            text = formatValor(medicion.valor),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge
        )

        // Línea 92: Aseguramos que el parámetro text sea String
        Text(
            text = medicion.fecha.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

// Nueva versión de la función para evitar conflictos de candidatos
fun formatValor(valor: Int): String {
    return java.text.NumberFormat.getInstance(java.util.Locale("es", "CL")).format(valor)
}