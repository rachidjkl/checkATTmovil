package com.example.menulateral.ui.visorAsistencia

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
import com.example.menulateral.DataModel.*
import com.example.menulateral.R
import com.example.menulateral.ui.justificarFalta.UFCheckBoxAdapter
import org.w3c.dom.Text
import java.math.RoundingMode
import java.text.DecimalFormat

class AdapterVisorAsistenciaProfe(
    private val ufAlumno: List<AlumnoUf>
):
    RecyclerView.Adapter<AdapterVisorAsistenciaProfe.VisorAsistenciaHolder>(), View.OnClickListener {


    private val layout = R.layout.card_view_alumno // Reemplaza "nuevo_layout" con el nombre del nuevo layout que has proporcionado
    private var clickListener: View.OnClickListener? = null

    class VisorAsistenciaHolder(view: View) : RecyclerView.ViewHolder(view) {
        var alumnName: TextView
        var porcentaje: TextView


        init {
            alumnName = view.findViewById(R.id.alumnName)
            porcentaje = view.findViewById(R.id.porcentaje)
        }
    }

    override fun onBindViewHolder(holder: VisorAsistenciaHolder, position: Int) {
        val alumno = ufAlumno[position]

        bindPackage(holder, alumno)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisorAsistenciaHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        view.setOnClickListener(this)
        return VisorAsistenciaHolder(view)
    }

    override fun getItemCount(): Int {
        return ufAlumno.size
    }

    fun bindPackage(holder: VisorAsistenciaHolder, alumnoUf: AlumnoUf) {


        holder.alumnName?.text = alumnoUf.nombreAlumno + " " + alumnoUf.apellido1_alumno + " " + alumnoUf.apellido2_alumno

        holder.porcentaje?.text = alumnoUf.porcentajeAsistencia.toString()


    }



    override fun onClick(view: View?) {
        clickListener?.onClick(view)
    }

    fun setOnClickListener(listener: View.OnClickListener) {
        clickListener = listener
    }
}