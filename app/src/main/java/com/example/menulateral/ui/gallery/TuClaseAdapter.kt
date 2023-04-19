package com.example.menulateral.ui.gallery

import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.menulateral.DataModel.Alumno
import com.example.menulateral.DataModel.Modulos
import com.example.menulateral.DataModel.Uf
import com.example.menulateral.R
import com.example.menulateral.ui.visorAsistencia.UfPercentAdapter

class TuClaseAdapter (private val AlumnosList : MutableList<Alumno>):
    RecyclerView.Adapter<TuClaseAdapter.TuClaseHolder>(), View.OnClickListener {

    private val layout = R.layout.card_view_alumno // Reemplaza "nuevo_layout" con el nombre del nuevo layout que has proporcionado
    private var clickListener: View.OnClickListener? = null

    class TuClaseHolder(view: View) : RecyclerView.ViewHolder(view) {
        var alumnName: TextView
        var porcentajetxt: TextView
        var cardview: CardView


        init {
            alumnName = view.findViewById(R.id.alumnName)
            cardview = view.findViewById(R.id.cardView1)
            porcentajetxt = view.findViewById(R.id.porcentaje)
        }
    }

    override fun onBindViewHolder(holder: TuClaseHolder, position: Int) {
        val alumno = AlumnosList[position]
        holder.cardview.setOnClickListener() {

        }
        bindPackage(holder, alumno)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TuClaseHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        view.setOnClickListener(this)
        return TuClaseHolder(view)
    }

    override fun getItemCount(): Int {
        return AlumnosList.size
    }

    fun bindPackage(holder: TuClaseHolder, alumno: Alumno) {

        holder.alumnName?.text = alumno.nombreAlumno+" "+alumno.apellido1Alumno+" "
        holder.porcentajetxt.text = "88"
    }

    override fun onClick(view: View?) {
        clickListener?.onClick(view)
    }

    fun setOnClickListener(listener: View.OnClickListener) {
        clickListener = listener
    }
}