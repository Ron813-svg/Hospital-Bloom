package RecyclerViewHelpers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.robbielarios.ronyramirez.hospitalbloom.R

class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
   val title: TextView = view.findViewById(R.id.txtTituloCard)
   val imgBorrar: ImageView = view.findViewById(R.id.imgBorrar)
   val imgEditar: ImageView = view.findViewById(R.id.imgActualizar)



}