package cl.brbc.example.controlgastosapp.data

import android.app.Application
import androidx.room.Room


class MedicionApp: Application() {
    val db by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "mediciones_db").build()
    }
    val medicionDao by lazy { db.medicionDao() }
}