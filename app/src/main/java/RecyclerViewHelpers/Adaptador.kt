package RecyclerViewHelpers

import Modelos.Conexion
import Modelos.tbPacientes
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.robbielarios.ronyramirez.hospitalbloom.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Adaptador(private var Data: List<tbPacientes>): RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return ViewHolder(view)
    }

    fun ActualizarListaDespuesDeEditar(uuid: String, nuevoNombre: String){
        val Index = Data.indexOfFirst { it.uuid == uuid }
        Data[Index].nombrePaciente = nuevoNombre
        notifyItemChanged(Index)
    }

    override fun getItemCount() = Data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = Data[position]
        holder.title.text = item.nombrePaciente

        holder.imgBorrar.setOnClickListener {


            val context = holder.itemView.context
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Confirmación")
            builder.setMessage("¿Está seguro que quiere borrar?")

            builder.setPositiveButton("Si") { dialog, wich ->
                eliminarDatos(item.nombrePaciente, position)
            }
            builder.setNegativeButton("No") { dialog, wich ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()

        }


        holder.imgEditar.setOnClickListener {
            val context = holder.itemView.context
            val dialogView = LayoutInflater.from(context).inflate(R.layout.activity_actualizar, null)
            val txtNombre_A = dialogView.findViewById<EditText>(R.id.txtNombre_A)
            val txtEnfermedad_A = dialogView.findViewById<EditText>(R.id.txtTipoSangre_A)
            val txtTelefono_A = dialogView.findViewById<EditText>(R.id.txtTelefono_A)
            val txtTipoSangre_A = dialogView.findViewById<EditText>(R.id.txtEnfermedad_A)
            val txtFechaNacimiento_A = dialogView.findViewById<EditText>(R.id.txtFechaNacimiento_A)
            val txtHoraMedicacion_A = dialogView.findViewById<EditText>(R.id.txtHoraMedicacion_A)
            val txtNumeroHabitacion_A = dialogView.findViewById<EditText>(R.id.txtNumeroHabitacion_A)
            val txtNumeroCama_A = dialogView.findViewById<EditText>(R.id.txtNumeroCama_A)
            val txtMedicamentos_A = dialogView.findViewById<EditText>(R.id.txtMedicamentos_A)



            txtNombre_A.setText(item.nombrePaciente)
            txtEnfermedad_A.setText(item.enfermedadPaciente)
            txtTelefono_A.setText(item.telefonoPaciente)
            txtTipoSangre_A.setText(item.tipoSangrePaciente)
            txtFechaNacimiento_A.setText(item.fechaNacimientoPaciente)
            txtHoraMedicacion_A.setText(item.horaAplicacionPaciente)
            txtNumeroHabitacion_A.setText(item.numHabitacionPaciente?.toString() ?: "")
            txtNumeroCama_A.setText(item.numCamaPaciente?.toString() ?: "")
            txtMedicamentos_A.setText(item.medicamentosPaciente)

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Actualizar este Paciente")
            builder.setView(dialogView)

            builder.setPositiveButton("Actualizar") { dialog, _ ->
                val nombrePaciente = txtNombre_A.text.toString()
                val tipoSangre = txtTipoSangre_A.text.toString()
                val telefono = txtTelefono_A.text.toString()
                val enfermedad = txtEnfermedad_A.text.toString()
                val fechaNacimiento = txtFechaNacimiento_A.text.toString()
                val horaMedicion = txtHoraMedicacion_A.text.toString()
                val numeroHabitacion = txtNumeroHabitacion_A.text.toString().takeIf { it.isNotEmpty() }?.toIntOrNull()
                val numeroCama = txtNumeroCama_A.text.toString().takeIf { it.isNotEmpty() }?.toIntOrNull()
                val medicamentos = txtMedicamentos_A.text.toString()
                actualizarDato(nombrePaciente, tipoSangre, telefono, enfermedad, fechaNacimiento, horaMedicion, numeroHabitacion, numeroCama, medicamentos, item.uuid, GlobalScope)
                dialog.dismiss()
            }

            builder.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }
        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putString("Nombre", item.nombrePaciente)
                putString("TSangre", item.tipoSangrePaciente)
                putString("Telefono", item.telefonoPaciente)
                putString("Enfermedad", item.enfermedadPaciente)
                putString("NumHabitacion", item.numHabitacionPaciente)
                putString("NumCama", item.numCamaPaciente)
                putString("Medicamento", item.medicamentosPaciente)
                putString("FechaNac", item.fechaNacimientoPaciente)
                putString("HoraMedicacion", item.horaAplicacionPaciente)
            }
            val navController = findNavController(holder.itemView)
            navController.navigate(R.id.detalles2, bundle)
        }
    }
    private fun findNavController(view: View): NavController {
        val fragment = view.findFragment<Fragment>()
        return NavHostFragment.findNavController(fragment)
    }

    fun setData(newData: List<tbPacientes>) {
        Data = newData
        notifyDataSetChanged()
    }

    //aca empiezo yo xd

    fun actualizarDato(
        Nombre: String,
        TSangre: String?,
        Telefono: String?,
        Enfermedad: String?,
        FechaNac: String?,
        HoraM: String?,
        NumHabitacion: Int?,
        NumCama: Int?,
        Medicamento: String?,
        uuid: String,
        scope: CoroutineScope
    ) {
        scope.launch(Dispatchers.IO) {
            // 1- Creo un obj de la clase conexion
            val objConexion = Conexion().cadenaConexion()

            // 2- Creo una variable que contenga un PrepareStatement
            val updatePaciente = objConexion?.prepareStatement(
                "UPDATE pacientes SET " + "Nombre = ?, TSangre = ?, telefono = ?, enfermedad = ?, " + "FechaNac = ?, HoraMedicacion = ?, NumHabitacion = ?, " + "NumCama = ?, Medicamento = ? WHERE uuid = ?"
            )
            updatePaciente?.apply {
                setString(1, Nombre)
                setString(2, TSangre)
                setString(3, Telefono)
                setString(4, Enfermedad)
                setString(5, FechaNac)
                setString(6, HoraM)
                setObject(7, NumHabitacion)
                setObject(8, NumCama)
                setString(9, Medicamento)
                setString(10, uuid)
                executeUpdate()
            }

        }
    }



    /////////// todo: Eliminar datos
    fun eliminarDatos(nombrePaciente: String, posicion: Int){
        //Eliminarlo de la lista
        val listaDatos = Data.toMutableList()
        listaDatos.removeAt(posicion)

        //Eliminarlo de la base de datos
        GlobalScope.launch(Dispatchers.IO){
            //1- Creo un objeto de la clase conexion
            val objConexion = Conexion().cadenaConexion()

            //2- creo una variable que contenga
            //un PrepareStatement
            val deletePaciente = objConexion?.prepareStatement("delete pacientes where Nombre = ?")!!
            deletePaciente.setString(1, nombrePaciente)
            deletePaciente.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()
        }
        Data = listaDatos.toList()
        notifyItemRemoved(posicion)
        notifyDataSetChanged()
    }



}
