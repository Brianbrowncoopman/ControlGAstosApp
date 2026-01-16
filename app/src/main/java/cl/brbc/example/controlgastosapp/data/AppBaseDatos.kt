package cl.brbc.example.controlgastosapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

// Uso esta anotación para declarar que esta es mi base de datos de Room.
// Indico que la tabla será 'Medicion', que es la versión 1 y que no necesito exportar el esquema.
@Database(entities = [Medicion::class], version = 1, exportSchema = false)
// Activo aquí mi clase 'LocalDateConverter' para que toda la base de datos sepa cómo manejar las fechas.
@TypeConverters(LocalDateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    // Defino esta función abstracta para poder acceder a los métodos de mi DAO (insertar, borrar, etc.).
    abstract fun medicionDao(): MedicionDao

}