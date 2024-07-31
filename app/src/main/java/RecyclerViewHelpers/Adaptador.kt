package RecyclerViewHelpers

import Modelos.tbPacientes
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.robbielarios.ronyramirez.hospitalbloom.R

class Adaptador(private var Data: List<tbPacientes>): RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = Data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = Data[position]
        holder.title.text = item.nombrePaciente
    }

    fun setData(newData: List<tbPacientes>) {
        Data = newData
        notifyDataSetChanged()
    }
}
