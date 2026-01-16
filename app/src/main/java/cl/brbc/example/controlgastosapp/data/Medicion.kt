package cl.brbc.example.controlgastosapp.data

// Importo las herramientas de Room para convertir esta clase en una tabla de base de datos
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

// Defino que esta clase será una entidad de Room y le asigno el nombre "mediciones" a la tabla
@Entity(tableName = "mediciones")
data class Medicion(
    // Configuro el ID como llave primaria y le pido a Room que genere los números automáticamente
    @PrimaryKey(autoGenerate = true)
    val id   : Int = 0, // Aquí guardo el identificador único de cada registro

    // Defino el campo para almacenar si la medición es de agua, luz o gas
    val tipo : String,

    // Defino el campo para guardar el valor numérico que ingresa el usuario
    val valor: Int,

    // Defino el campo para la fecha, usando el tipo LocalDate que manejamos en el calendario
    val fecha: LocalDate
)