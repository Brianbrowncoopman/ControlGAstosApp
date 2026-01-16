package cl.brbc.example.controlgastosapp.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDate

// Uso esta clase para que Room sepa cómo guardar objetos LocalDate en la base de datos,
// ya que SQLite solo entiende tipos de datos simples como números o textos.
class LocalDateConverter {

    // Esta anotación indica que estas funciones solo se ejecutarán en dispositivos
    // con Android 8.0 o superior, solucionando los errores de "API level 26".
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? {
        // Con esta función tomo el número largo que guardé en la base de datos
        // y lo transformo de nuevo en una fecha (LocalDate) para usarla en mi app.
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Long? {
        // Con esta función tomo la fecha de mi objeto Medicion y la convierto
        // en un número de días para que Room pueda almacenarla físicamente.
        return date?.toEpochDay()
    }
}