package cl.brbc.example.controlgastosapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.brbc.example.controlgastosapp.data.Medicion
import cl.brbc.example.controlgastosapp.data.MedicionDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MedicionViewModel(private val medicionDao: MedicionDao): ViewModel() {
    val todasLAsMediciones : StateFlow<List<Medicion>> = medicionDao.obtenerTodas()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5000),
            initialValue = emptyList()
        )

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
}