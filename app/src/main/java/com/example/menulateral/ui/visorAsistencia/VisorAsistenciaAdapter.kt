package com.example.menulateral.ui.visorAsistencia

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.menulateral.Faltas
import com.example.menulateral.Modulos
import com.example.menulateral.R
import com.example.menulateral.Uf
import com.example.menulateral.ui.faltasJustificadas.FaltasJustificadasAdapter
import com.example.menulateral.ui.faltasJustificadas.UfColorRectangleAdapter

class VisorAsistenciaAdapter (private val ufList: MutableList<Uf>, private val moduloList : MutableList<Modulos>):
    RecyclerView.Adapter<VisorAsistenciaAdapter.VisorAsistenciaHolder>(), View.OnClickListener {
    val adapter = UfPercentAdapter(ufList)

    private val layout = R.layout.module_item // Reemplaza "nuevo_layout" con el nombre del nuevo layout que has proporcionado
    private var clickListener: View.OnClickListener? = null

    class VisorAsistenciaHolder(view: View) : RecyclerView.ViewHolder(view) {
        var moduloName: TextView
        var recyclerViewRellenar: RecyclerView
        var deployModule: ImageView
        var cardview: CardView


        init {
            moduloName = view.findViewById(R.id.moduleName)
            recyclerViewRellenar = view.findViewById(R.id.recyclerUf)
            recyclerViewRellenar.layoutManager = LinearLayoutManager(view.context)
            deployModule = view.findViewById(R.id.deployModule)
            cardview = view.findViewById(R.id.cardView1)
        }
    }

    override fun onBindViewHolder(holder: VisorAsistenciaHolder, position: Int) {
        val modulo = moduloList[position]
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
        bindPackage(holder, modulo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisorAsistenciaHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        view.setOnClickListener(this)
        return VisorAsistenciaHolder(view)
    }

    override fun getItemCount(): Int {
        return moduloList.size
    }

    fun bindPackage(holder: VisorAsistenciaHolder, modulo: Modulos) {

        holder.moduloName?.text = modulo.siglas
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