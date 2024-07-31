package com.robbielarios.ronyramirez.hospitalbloom.ui.dashboard

import Modelos.Conexion
import Modelos.tbPacientes
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.robbielarios.ronyramirez.hospitalbloom.R
import com.robbielarios.ronyramirez.hospitalbloom.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root



        val rcvPacientes: RecyclerView = root.findViewById(R.id.rcvPacientes)

        rcvPacientes.layoutManager = LinearLayoutManager(requireContext())

        fun obtenerDatos(): List<tbPacientes> {
            val objConnection = Conexion().cadenaConexion()
            val query = objConnection?.createStatement()
            val result = query?.executeQuery("SELECT * FROM pacientes")!!

            val listaPacientes = mutableListOf<tbPacientes>()

            while (result.next()) {
                val id = result.getInt("idPaciente")
                val nombre = result.getString("Nombre")
                val tipoSangre = result.getString("TSangre")
                val telefono = result.getString("Telefono")
                val enfermedad = result.getString("Enfermedad")
                val numHabitacion = result.getString("NumHabitacion")
                val numCama = result.getString("NumCama")
                val medicamentos = result.getString("Medicamento")
                val fechaNacimiento = result.getString("FechaNac")
                val horaAplicacion = result.getString("HoraMedicacion")

                val allValues = tbPacientes(id, nombre, tipoSangre, telefono, enfermedad, numHabitacion, numCama, medicamentos, fechaNacimiento, horaAplicacion)

                listaPacientes.add(allValues)

            }
            return listaPacientes
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}