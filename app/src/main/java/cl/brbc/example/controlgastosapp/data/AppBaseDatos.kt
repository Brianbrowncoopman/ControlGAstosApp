package cl.brbc.example.controlgastosapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Medicion::class], version = 1, exportSchema = false)
@TypeConverters(LocalDateConverter::class)//activa el convertidor
abstract class AppDatabase : RoomDatabase() {
    abstract fun medicionDao(): MedicionDao

}