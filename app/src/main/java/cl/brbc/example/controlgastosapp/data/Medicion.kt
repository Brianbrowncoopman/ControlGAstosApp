package cl.brbc.example.controlgastosapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

//Estructura de datos
@Entity(tableName = "mediciones")
data class Medicion(
    @PrimaryKey(autoGenerate = true)
    val id   : Int = 0, // Identificador unico
    val tipo : String, //tipo de medicion agua, luz, gas
    val valor: Int, //valor de la medicion
    val fecha: LocalDate // fecha del registro
)