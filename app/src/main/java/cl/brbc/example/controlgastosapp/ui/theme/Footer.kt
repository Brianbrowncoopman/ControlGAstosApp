package cl.brbc.example.controlgastosapp.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cl.brbc.example.controlgastosapp.R

@Composable
fun FooterApp() {
    // Utilizamos Surface para aplicar el fondo gris claro a todo el bloque
    Surface(
        color = Color(0xFFF2F2F2), // Gris claro (puedes usar Color.LightGray si prefieres)
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally // Centrado horizontal de la columna
        ) {
            // Línea divisoria sutil arriba
            HorizontalDivider(
                thickness = 0.5.dp,
                color = Color.Gray.copy(alpha = 0.3f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Stack vertical para asegurar que todo esté centrado
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.footer_author),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black, // Letras negras
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(id = R.string.footer_version),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black.copy(alpha = 0.7f), // Negro un poco más suave
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(id = R.string.footer_app_name),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black, // Letras negras
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}