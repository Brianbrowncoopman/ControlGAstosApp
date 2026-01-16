package cl.brbc.example.controlgastosapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.brbc.example.controlgastosapp.data.Medicion
import cl.brbc.example.controlgastosapp.data.MedicionDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// Defino mi clase ViewModel, la cual se encarga de mantener los datos vivos aunque la pantalla rote.
// Recibo el 'medicionDao' para poder comunicarme con la base de datos Room.
class MedicionViewModel(private val medicionDao: MedicionDao): ViewModel() {

    // Aquí creo un flujo de datos (StateFlow) que contiene la lista de todas las mediciones.
    // Transformo el flujo del DAO en un estado que la interfaz de usuario puede observar fácilmente.
    val todasLAsMediciones : StateFlow<List<Medicion>> = medicionDao.obtenerTodas()
        .stateIn(
            scope = viewModelScope, // Uso el alcance del ViewModel para que el flujo se limpie cuando ya no se use.
            started = SharingStarted.Companion.WhileSubscribed(5000), // Mantengo el flujo activo 5 segundos después de cerrar la app por eficiencia.
            initialValue = emptyList() // Comienzo con una lista vacía mientras cargan los datos.
        )

    // Creo esta función para guardar una nueva medición.
    fun insertarMedicion(medicion: Medicion){
        // Lanzo una corrutina en el hilo secundario para no bloquear la pantalla mientras se guarda en el disco.
        viewModelScope.launch {
            medicionDao.insertar(medicion)
        }
    }

    // Creo esta función para borrar un registro específico.
    fun eliminarMedicion(medicion: Medicion) {
        // También uso una corrutina para asegurar que la eliminación ocurra de forma segura fuera del hilo principal.
        viewModelScope.launch {
            medicionDao.eliminar(medicion)
        }
    }
}