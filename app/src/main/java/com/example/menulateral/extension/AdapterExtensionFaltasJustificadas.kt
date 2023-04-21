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
import com.example.menulateral.DataModel.FaltasPorFecha
import com.example.menulateral.R

class AdapterExtensionFaltasJustificadas (private val faltasfecha: MutableList<FaltasPorFecha>):
    RecyclerView.Adapter<AdapterExtensionFaltasJustificadas.AdapterExtensionFaltasJustificadasHolder>(), View.OnClickListener {

    private val layout = R.layout.card_view_extension_falta_judtificada // Reemplaza "nuevo_layout" con el nombre del nuevo layout que has proporcionado
    private var clickListener: View.OnClickListener? = null

    class AdapterExtensionFaltasJustificadasHolder(view: View) : RecyclerView.ViewHolder(view) {
        var moduloName: TextView
        var recyclerViewRellenar: RecyclerView
        var deployModule: ImageView
        var cardview: CardView


        init {
            moduloName = view.findViewById(R.id.alumnName)
            recyclerViewRellenar = view.findViewById(R.id.recyclerUf)
            recyclerViewRellenar.layoutManager = LinearLayoutManager(view.context)
            deployModule = view.findViewById(R.id.deployModuleColorRectangle)
            cardview = view.findViewById(R.id.cardView1)
        }
    }

    override fun onBindViewHolder(holder:AdapterExtensionFaltasJustificadasHolder, position: Int) {
        val faltafecha = faltasfecha[position]
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
        bindPackage(holder, faltafecha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterExtensionFaltasJustificadasHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        view.setOnClickListener(this)
        return AdapterExtensionFaltasJustificadasHolder(view)
    }

    override fun getItemCount(): Int {
        return faltasfecha.size
    }

    fun bindPackage(holder: AdapterExtensionFaltasJustificadasHolder, faltafecha: FaltasPorFecha) {

        val adapter = UfPercentAdapter(faltafecha.faltas)

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