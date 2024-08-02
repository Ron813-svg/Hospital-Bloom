package com.robbielarios.ronyramirez.hospitalbloom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Detalles : Fragment() {
    companion object {
        fun newInstance(
            Nombre: String,
            TSangre: String,
            Telefono: String,
            Enfermedad: String,
            NumHabitacion: String,
            NumCama: String,
            Medicamento: String,
            FechaNac: String,
            HoraM: String
        ): Detalles {
            val fragment = Detalles()
            val args = Bundle()
            args.putString("Nombre", Nombre)
            args.putString("Tsangre", TSangre)
            args.putString("Telefono", Telefono)
            args.putString("Enfermedad", Enfermedad)
            args.putString("NumHabitacion", NumHabitacion)
            args.putString("NumCama", NumCama)
            args.putString("Medicamento", Medicamento)
            args.putString("FechaNac", FechaNac)
            args.putString("HoraMedicacion", HoraM)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_detalles, container, false)

        val Ic_Regresar = root.findViewById<ImageView>(R.id.regresar)

        val Nombre_O = arguments?.getString("Nombre")
        val Tipo_Sangre_O = arguments?.getString("TSangre")
        val Telefono_O = arguments?.getString("Telefono")
        val Enfermedad_O = arguments?.getString("Enfermedad")
        val NumHabitacion_O = arguments?.getString("NumHabitacion")
        val NumCama_O = arguments?.getString("NumCama")
        val Medicamento_O = arguments?.getString("Medicamento")
        val FechaNac_O = arguments?.getString("FechaNac")
        val HoraM_O = arguments?.getString("HoraMedicacion")


        val LBL_Nombre = root.findViewById<TextView>(R.id.lblNombre_D)
        val LBL_Tsangre = root.findViewById<TextView>(R.id.lblTsangre_D)
        val LBL_Telefono = root.findViewById<TextView>(R.id.lblTelefono_D)
        val LBL_Enfermedad = root.findViewById<TextView>(R.id.lblEnfermedad_D)
        val LBL_Habitacion = root.findViewById<TextView>(R.id.lblMedicamento_D)
        val LBL_Cama = root.findViewById<TextView>(R.id.lblFechaNac_D)
        val LBL_Medicamento = root.findViewById<TextView>(R.id.lblHoraM_D)
        val LBL_Nacimiento = root.findViewById<TextView>(R.id.lblNumHabitacion_D)
        val LBL_Hora_Medicacion = root.findViewById<TextView>(R.id.lblNumCama_D)

        LBL_Nombre.text = Nombre_O
        LBL_Tsangre.text = Tipo_Sangre_O
        LBL_Telefono.text = Telefono_O
        LBL_Enfermedad.text = Enfermedad_O
        LBL_Habitacion.text = NumHabitacion_O.toString()
        LBL_Cama.text = NumCama_O.toString()
        LBL_Medicamento.text = Medicamento_O
        LBL_Nacimiento.text = FechaNac_O
        LBL_Hora_Medicacion.text = HoraM_O


        Ic_Regresar.setOnClickListener {
            findNavController().navigate(R.id.navigation_dashboard)
        }

        return root
    }
}