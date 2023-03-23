package com.example.menulateral.ui.faltasJustificadas

import android.content.Context
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.menulateral.R
import com.example.menulateral.Uf
import com.example.menulateral.ui.Faltas

class FaltasJustificadasAdapter( val ufList: MutableList<Uf>, private val faltasList : MutableList<Faltas>):
    RecyclerView.Adapter<FaltasJustificadasAdapter.FaltasJustificadasHolder>(), View.OnClickListener{
    val adapter = UfColorRectangleAdapter(ufList)

    private val layout = R.layout.adapter_item_color // Reemplaza "nuevo_layout" con el nombre del nuevo layout que has proporcionado
    private var clickListener: View.OnClickListener? = null

    class FaltasJustificadasHolder (val view: View): RecyclerView.ViewHolder(view){
        var alumnName: TextView
        var recyclerViewRellenar: RecyclerView
        val deployModule : ImageView


        init {
            alumnName = view.findViewById(R.id.alumnName)
            recyclerViewRellenar = view.findViewById(R.id.recyclerUfColorRectangle)
            recyclerViewRellenar.layoutManager = LinearLayoutManager(view.context)
            deployModule = view.findViewById(R.id.deployModuleColorRectangle)
        }
    }

    override fun onBindViewHolder(holder: FaltasJustificadasAdapter.FaltasJustificadasHolder, position: Int) {
        val falta = faltasList[position]
        bindPackage(holder, falta)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaltasJustificadasHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        view.setOnClickListener(this)
        return FaltasJustificadasHolder(view)
    }

    override fun getItemCount(): Int {
        return faltasList.size
    }

    fun bindPackage(holder: FaltasJustificadasHolder, falta: Faltas){

        holder.alumnName?.text = falta.fecha
        holder.recyclerViewRellenar?.adapter= adapter
        holder.recyclerViewRellenar.visibility = View.GONE

    }

    override fun onClick(view: View?) {
        clickListener?.onClick(view)
    }

    fun setOnClickListener(holder: FaltasJustificadasHolder, listener: View.OnClickListener){
        clickListener = listener
        fun expand2(view: View) {

            if (holder.recyclerViewRellenar.visibility == View.GONE) {
                // Creamos un objeto Transition que afecte a los cambios en las vistas
                val transition = ChangeBounds()

                // Indicamos el tiempo de duración de la animación
                transition.duration = 500

                // Indicamos que se animen los cambios de manera fluida
                transition.interpolator = AccelerateDecelerateInterpolator()

                // Llamamos a la función beginDelayedTransition con nuestro ViewGroup y nuestro Transition
                TransitionManager.beginDelayedTransition(view as ViewGroup, transition)
                holder.recyclerViewRellenar.visibility = View.VISIBLE
                holder.deployModule.setImageResource(R.drawable.expand_less)
            } else {
                holder.recyclerViewRellenar.visibility = View.GONE
                holder.deployModule.setImageResource(R.drawable.expand_more)
            }
        }
    }
}