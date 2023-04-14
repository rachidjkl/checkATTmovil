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
import com.example.menulateral.DataModel.FaltaToShow
import com.example.menulateral.DataModel.Faltas
import com.example.menulateral.DataModel.FaltasPorFecha
import com.example.menulateral.R
import com.example.menulateral.DataModel.Uf
import com.example.menulateral.ui.justificarFalta.UFCheckBoxAdapter

class justificarFaltaAdapter ( private val faltasfecha : List<FaltasPorFecha>):
    RecyclerView.Adapter<justificarFaltaAdapter.justificarFaltaHolder>(), View.OnClickListener {

    private val layout = R.layout.attendance_item_checkbox // Reemplaza "nuevo_layout" con el nombre del nuevo layout que has proporcionado
    private var clickListener: View.OnClickListener? = null

    class justificarFaltaHolder(view: View) : RecyclerView.ViewHolder(view) {
        var moduloName: TextView
        var recyclerViewRellenar: RecyclerView
        var deployModule: ImageView
        var cardview: CardView


        init {
            moduloName = view.findViewById(R.id.dataAbsence)
            recyclerViewRellenar = view.findViewById(R.id.recyclerUfCkeckBox)
            recyclerViewRellenar.layoutManager = LinearLayoutManager(view.context)
            deployModule = view.findViewById(R.id.deployModuleCheckBox)
            cardview = view.findViewById(R.id.cardView1)
        }
    }

    override fun onBindViewHolder(holder: justificarFaltaHolder, position: Int) {
        val falta = faltasfecha[position]
        holder.cardview.setOnClickListener() {
            if (holder.recyclerViewRellenar.visibility == View.GONE) {
                // Creamos un objeto Transition que afecte a los cambios en las vistas
                val transition = ChangeBounds()

                // Indicamos el tiempo de duración de la animación
                transition.duration = 300

                // Indicamos que se animen los cambios de manera fluida
                transition.interpolator = AccelerateDecelerateInterpolator()

                // Llamamos a la función beginDelayedTransition con nuestro ViewGroup y nuestro Transition
                TransitionManager.beginDelayedTransition(holder.cardview as ViewGroup, transition)
                holder.recyclerViewRellenar.visibility = View.VISIBLE

                holder.deployModule.setImageResource(R.drawable.expand_less)
            } else {
                holder.recyclerViewRellenar.visibility = View.GONE
                holder.deployModule.setImageResource(R.drawable.expand_more)
            }
        }
        bindPackage(holder, falta)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): justificarFaltaHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        view.setOnClickListener(this)
        return justificarFaltaHolder(view)
    }

    override fun getItemCount(): Int {
        return faltasfecha.size
    }

    fun bindPackage(holder: justificarFaltaHolder, faltafecha: FaltasPorFecha) {

        val adapter = UFCheckBoxAdapter(faltafecha.faltas,false) //ADAPTER INTERNO DEL CARDVIEW

        holder.moduloName?.text = faltafecha.fecha.toString()
        holder.recyclerViewRellenar?.adapter = adapter
        holder.recyclerViewRellenar.visibility = View.GONE

    }

    override fun onClick(view: View?) {
        clickListener?.onClick(view)
    }

    fun setOnClickListener(listener: View.OnClickListener) {
        clickListener = listener
    }
}