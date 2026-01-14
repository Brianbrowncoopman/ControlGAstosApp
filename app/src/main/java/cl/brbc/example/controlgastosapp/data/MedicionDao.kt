package cl.brbc.example.controlgastosapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicionDao {

    // trae todas las mediciones ordenadas por fecha descendente
    @Query("SELECT * FROM mediciones ORDER BY fecha DESC")
    fun obtenerTodas(): Flow<List<Medicion>>

    // Busca por ID
    @Query("SELECT * FROM mediciones WHERE id = :id")
    suspend fun obtenerPorId(id: Int): Medicion

    //inserta
    @Insert
    suspend fun insertar(medicion: Medicion)

    //actualiza
    @Update
    suspend fun modificar(medicion: Medicion)

    //elimina
    @Delete
    suspend fun eliminar(medicion: Medicion)
}
