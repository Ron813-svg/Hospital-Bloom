package RecyclerViewHelpers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.robbielarios.ronyramirez.hospitalbloom.R

class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
   val title: TextView = view.findViewById(R.id.txtTituloCard)
   val image: ImageView = view.findViewById(R.id.imgBorrar)


}