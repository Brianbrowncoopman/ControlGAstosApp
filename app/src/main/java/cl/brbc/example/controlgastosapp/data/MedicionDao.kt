package cl.brbc.example.controlgastosapp.data

// Importo las anotaciones de Room para definir las operaciones de acceso a datos
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
// Importo Flow para poder observar los cambios en la base de datos en tiempo real
import kotlinx.coroutines.flow.Flow

// Declaro esta interfaz como un DAO para que Room sepa que aquí están las consultas SQL
@Dao
interface MedicionDao {

    // Escribo una consulta SQL para traer todas las mediciones de la tabla, ordenadas de la más reciente a la más antigua
    // Uso Flow para que la lista en la pantalla se actualice sola cada vez que hay un cambio
    @Query("SELECT * FROM mediciones ORDER BY fecha DESC")
    fun obtenerTodas(): Flow<List<Medicion>>

    // Defino esta función para buscar una medición específica usando su ID único
    // Uso 'suspend' para que la búsqueda se ejecute fuera del hilo principal y no congele la app
    @Query("SELECT * FROM mediciones WHERE id = :id")
    suspend fun obtenerPorId(id: Int): Medicion

    // Uso la anotación Insert para indicarle a Room que esta función guarda un nuevo registro de Medicion
    @Insert
    suspend fun insertar(medicion: Medicion)

    // Uso la anotación Update para que Room busque el registro por su ID y actualice sus valores
    @Update
    suspend fun modificar(medicion: Medicion)

    // Uso la anotación Delete para eliminar permanentemente un registro específico de la tabla
    @Delete
    suspend fun eliminar(medicion: Medicion)
}
