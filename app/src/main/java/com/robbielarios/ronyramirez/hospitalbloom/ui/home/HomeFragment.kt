package com.robbielarios.ronyramirez.hospitalbloom.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import Modelos.Conexion
import Modelos.tbPacientes
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.robbielarios.ronyramirez.hospitalbloom.R
import com.robbielarios.ronyramirez.hospitalbloom.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import viewModel.SharedViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val btnAdd: Button = root.findViewById(R.id.btnIngresar)

        val txtNombre: TextView = root.findViewById(R.id.txtNombre)
        val txtTipoSangre: TextView = root.findViewById(R.id.txtTipoSangre)
        val txtTelefono: TextView = root.findViewById(R.id.txtTelefono)
        val txtEnfermedad: TextView = root.findViewById(R.id.txtEnfermedad)
        val txtNumHabitacion: TextView = root.findViewById(R.id.txtNumHabitacion)
        val txtNumCama: TextView = root.findViewById(R.id.txtNumCama)
        val txtMedicamentos: TextView = root.findViewById(R.id.txtMedicamentos)
        val txtFechaNacimiento: TextView = root.findViewById(R.id.txtFechaNacimiento)
        val txtHoraAplicacion: TextView = root.findViewById(R.id.txtHoraAplicacion)

        btnAdd.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val objConnection = Conexion().cadenaConexion()
                val AddPaciente = objConnection?.prepareStatement(
                    "INSERT INTO pacientes (uuid, Nombre, TSangre, Telefono, Enfermedad, NumCama, NumHabitacion, Medicamento, FechaNac, HoraMedicacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                )!!
                val uuid = java.util.UUID.randomUUID().toString()
                AddPaciente.setString(1, uuid)
                AddPaciente.setString(2, txtNombre.text.toString())
                AddPaciente.setString(3, txtTipoSangre.text.toString())
                AddPaciente.setString(4, txtTelefono.text.toString())
                AddPaciente.setString(5, txtEnfermedad.text.toString())
                AddPaciente.setString(6, txtNumCama.text.toString())
                AddPaciente.setString(7, txtNumHabitacion.text.toString())
                AddPaciente.setString(8, txtMedicamentos.text.toString())
                AddPaciente.setString(9, txtFechaNacimiento.text.toString())
                AddPaciente.setString(10, txtHoraAplicacion.text.toString())
                AddPaciente.executeUpdate()

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Paciente Agregado", Toast.LENGTH_SHORT).show()
                    val newPaciente = tbPacientes(
                        uuid,
                        txtNombre.text.toString(),
                        txtTipoSangre.text.toString(),
                        txtTelefono.text.toString(),
                        txtEnfermedad.text.toString(),
                        txtNumHabitacion.text.toString(),
                        txtNumCama.text.toString(),
                        txtMedicamentos.text.toString(),
                        txtFechaNacimiento.text.toString(),
                        txtHoraAplicacion.text.toString()
                    )
                    sharedViewModel.addPaciente(newPaciente)

                    txtNombre.setText("")
                    txtTipoSangre.setText("")
                    txtTelefono.setText("")
                    txtEnfermedad.setText("")
                    txtNumHabitacion.setText("")
                    txtNumCama.setText("")
                    txtMedicamentos.setText("")
                    txtFechaNacimiento.setText("")
                    txtHoraAplicacion.setText("")
                }
            }
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
