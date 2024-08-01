package viewModel

import Modelos.Conexion
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import Modelos.tbPacientes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SharedViewModel : ViewModel() {
    private val _pacientes = MutableLiveData<List<tbPacientes>>()
    val pacientes: LiveData<List<tbPacientes>> get() = _pacientes

    init {
        loadPacientes()
    }

    private fun loadPacientes() {
        CoroutineScope(Dispatchers.IO).launch {
            val listaPacientes = mutableListOf<tbPacientes>()
            val objConnection = Conexion().cadenaConexion()

            if (objConnection != null) {
                val query = objConnection.createStatement()
                val result = query.executeQuery("select * from pacientes")

                while (result.next()) {
                    val uuid = result.getString("UUID")
                    val nombre = result.getString("Nombre")
                    val tipoSangre = result.getString("TSangre")
                    val telefono = result.getString("Telefono")
                    val enfermedad = result.getString("Enfermedad")
                    val numHabitacion = result.getString("NumHabitacion")
                    val numCama = result.getString("NumCama")
                    val medicamentos = result.getString("Medicamento")
                    val fechaNacimiento = result.getString("FechaNac")
                    val horaAplicacion = result.getString("HoraMedicacion")

                    val allValues = tbPacientes(uuid, nombre, tipoSangre, telefono, enfermedad, numHabitacion, numCama, medicamentos, fechaNacimiento, horaAplicacion)
                    listaPacientes.add(allValues)
                }

                result.close()
                query.close()
                objConnection.close()
            }

            withContext(Dispatchers.Main) {
                _pacientes.value = listaPacientes
            }
        }
    }

    fun addPaciente(paciente: tbPacientes) {
        val currentList = _pacientes.value?.toMutableList() ?: mutableListOf()
        currentList.add(paciente)
        _pacientes.value = currentList
    }
}




