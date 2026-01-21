package cl.brbc.example.controlgastosapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cl.brbc.example.controlgastosapp.data.Medicion
import cl.brbc.example.controlgastosapp.ui.theme.FooterApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaMedicionesScreen(
    viewModel: MedicionViewModel,
    onNavegarAFormulario: (Int?) -> Unit
) {
    // Observamos los estados del ViewModel
    val listaMediciones by viewModel.todasLAsMediciones.collectAsState()
    val textoBusqueda by viewModel.textoBusqueda.collectAsState()

    Scaffold(
        topBar = {
            Surface(shadowElevation = 4.dp) {
                Column {
                    CenterAlignedTopAppBar(
                        title = { Text(stringResource(R.string.title_list)) }
                    )
                    // BARRA DE BÚSQUEDA DINÁMICA
                    OutlinedTextField(
                        value = textoBusqueda,
                        onValueChange = { viewModel.actualizarBusqueda(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        placeholder = { Text("Buscar por tipo o fecha...") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        trailingIcon = {
                            if (textoBusqueda.isNotEmpty()) {
                                IconButton(onClick = { viewModel.actualizarBusqueda("") }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Limpiar")
                                }
                            }
                        },
                        singleLine = true,
                        shape = MaterialTheme.shapes.medium,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavegarAFormulario(null) }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },
        bottomBar = {
            FooterApp()
        }
    ) { padding ->
        // Si la lista filtrada está vacía, mostramos un aviso
        if (listaMediciones.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (textoBusqueda.isEmpty()) "No hay registros" else "Sin resultados para la búsqueda",
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                items(listaMediciones) { medicion ->
                    MedicionItem(
                        medicion = medicion,
                        onBorrar = { viewModel.eliminarMedicion(it) },
                        onEditar = { onNavegarAFormulario(medicion.id) }
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp)
                }
            }
        }
    }
}

@Composable
fun MedicionItem(
    medicion: Medicion,
    onBorrar: (Medicion) -> Unit,
    onEditar: () -> Unit
) {
    val backgroundColor = when (medicion.tipo.lowercase()) {
        "agua", "water" -> Color(0xFFE3F2FD)
        "luz", "electricity" -> Color(0xFFFFFDE7)
        "gas" -> Color(0xFFFFEBEE)
        else -> MaterialTheme.colorScheme.surface
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onEditar() },
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val iconRes = when (medicion.tipo.lowercase()) {
                "agua", "water" -> R.drawable.ic_water
                "luz", "electricity" -> R.drawable.ic_light
                else -> R.drawable.ic_gas
            }

            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = medicion.tipo.uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = formatValor(medicion.valor),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = medicion.fecha.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(end=4.dp)
                )
                IconButton(onClick = { onBorrar(medicion) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Borrar",
                        tint = Color(0xFFB71C1C)
                    )
                }
            }
        }
    }
}

fun formatValor(valor: Int): String {
    return java.text.NumberFormat.getInstance(java.util.Locale("es", "CL")).format(valor)
}