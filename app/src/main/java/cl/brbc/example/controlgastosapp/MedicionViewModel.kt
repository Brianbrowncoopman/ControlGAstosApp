package cl.brbc.example.controlgastosapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.brbc.example.controlgastosapp.data.Medicion
import cl.brbc.example.controlgastosapp.data.MedicionDao
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MedicionViewModel(private val medicionDao: MedicionDao): ViewModel() {

    // Estado para el texto de búsqueda que el usuario escribe
    private val _textoBusqueda = MutableStateFlow("")
    val textoBusqueda: StateFlow<String> = _textoBusqueda.asStateFlow()

    // Flujo original desde la base de datos
    private val medicionesBaseDeDatos = medicionDao.obtenerTodas()

    // Flujo filtrado: Combina los datos de la DB con el texto de búsqueda
    val todasLAsMediciones: StateFlow<List<Medicion>> = combine(
        medicionesBaseDeDatos,
        _textoBusqueda
    ) { lista, texto ->
        if (texto.isBlank()) {
            lista
        } else {
            lista.filter { medicion ->
                // Filtra por tipo (Agua, Luz, Gas) o por la fecha (formato texto)
                medicion.tipo.contains(texto, ignoreCase = true) ||
                        medicion.fecha.toString().contains(texto)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Función para actualizar el texto de búsqueda desde la pantalla
    fun actualizarBusqueda(nuevoTexto: String) {
        _textoBusqueda.value = nuevoTexto
    }

    fun insertarMedicion(medicion: Medicion){
        viewModelScope.launch {
            medicionDao.insertar(medicion)
        }
    }

    fun eliminarMedicion(medicion: Medicion) {
        viewModelScope.launch {
            medicionDao.eliminar(medicion)
        }
    }

    fun actualizarMedicion(medicion: Medicion) {
        viewModelScope.launch {
            medicionDao.modificar(medicion)
        }
    }

    // Para recuperar los datos antes de editar
    suspend fun obtenerMedicionPorId(id: Int): Medicion? {
        return medicionDao.obtenerPorId(id)
    }
}