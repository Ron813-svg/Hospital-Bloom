package RecyclerViewHelpers

import Modelos.Conexion
import Modelos.tbPacientes
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.robbielarios.ronyramirez.hospitalbloom.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Adaptador(private var Data: List<tbPacientes>): RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = Data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = Data[position]
        holder.title.text = item.nombrePaciente

        //Todo: clic al icono de borrar
        holder.imgBorrar.setOnClickListener {

            //Creo la alerta para confirmar la eliminacion
            //1- Invoco el contexto
            val context = holder.itemView.context
            //2- Creo la alerta
            // [Usando los tres pasos: titulo, mensaje y botones]
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Confirmación")
            builder.setMessage("¿Está seguro que quiere borrar?")

            builder.setPositiveButton("Si"){ dialog, wich ->
                eliminarDatos(item.nombrePaciente, position)
            }
            builder.setNegativeButton("No"){ dialog, wich ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()

        }
    }

    fun setData(newData: List<tbPacientes>) {
        Data = newData
        notifyDataSetChanged()
    }

    //aca empiezo yo xd

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
            val deleteCancion = objConexion?.prepareStatement("delete pacientes where Nombre = ?")!!
            deleteCancion.setString(1, nombrePaciente)
            deleteCancion.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()
        }
        Data = listaDatos.toList()
        notifyItemRemoved(posicion)
        notifyDataSetChanged()
    }



}
