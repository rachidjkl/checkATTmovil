package com.example.menulateral.ui.faltasJustificadas

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.menulateral.Faltas
import com.example.menulateral.JustificarFaltas
import com.example.menulateral.R
import com.example.menulateral.Uf
import com.google.android.material.tabs.TabLayout

class FaltasJustificadasAdapter(private val listener: FaltasJustificadasFragment,
                                FaltasList: MutableList<Faltas>, private val justificarFaltasList: MutableList<JustificarFaltas>, private val estado: Int):
    RecyclerView.Adapter<FaltasJustificadasAdapter.FaltasJustificadasHolder>(), View.OnClickListener{
    val adapter = UfColorRectangleAdapter(FaltasList)

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private val layout = R.layout.adapter_item_color // Reemplaza "nuevo_layout" con el nombre del nuevo layout que has proporcionado
    private var clickListener: View.OnClickListener? = null
    val square = ShapeDrawable(RectShape())

    class FaltasJustificadasHolder (view: View): RecyclerView.ViewHolder(view){
        var alumnName: TextView
        var recyclerViewRellenar: RecyclerView
        var deployModule : ImageView
        var imgCuadrado : ImageView
        var cardview : CardView
        var buttonVer : Button


        init {
            alumnName = view.findViewById(R.id.alumnName)
            recyclerViewRellenar = view.findViewById(R.id.recyclerUfColorRectangle)
            recyclerViewRellenar.layoutManager = LinearLayoutManager(view.context)
            deployModule = view.findViewById(R.id.deployModuleColorRectangle)
            cardview = view.findViewById(R.id.cardView1)
            imgCuadrado = view.findViewById(R.id.imgCuadradoOf)
            buttonVer = view.findViewById(R.id.botonVer)
        }
    }

    override fun onBindViewHolder(holder: FaltasJustificadasAdapter.FaltasJustificadasHolder, position: Int) {
        val justificarFalta = justificarFaltasList[position]
        val btn = holder.itemView.findViewById<Button>(R.id.botonVer)
        btn.setOnClickListener {
            listener.onItemClick(position)
        }
        holder.cardview.setOnClickListener(){
            if (holder.recyclerViewRellenar.visibility == View.GONE) {
                // Creamos un objeto Transition que afecte a los cambios en las vistas
                val transition = ChangeBounds()

                // Indicamos el tiempo de duración de la animación
                transition.duration = 300

                // Indicamos que se animen los cambios de manera fluida
                transition.interpolator = AccelerateDecelerateInterpolator()

                // Llamamos a la función beginDelayedTransition con nuestro ViewGroup y nuestro Transition
                TransitionManager.beginDelayedTransition( holder.cardview as ViewGroup, transition)
                holder.recyclerViewRellenar.visibility = View.VISIBLE
                holder.buttonVer.visibility = View.VISIBLE
                holder.deployModule.setImageResource(R.drawable.expand_less)
            } else {
                holder.recyclerViewRellenar.visibility = View.GONE
                holder.buttonVer.visibility = View.GONE
                holder.deployModule.setImageResource(R.drawable.expand_more)
            }
        }
        bindPackage(holder, justificarFalta)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaltasJustificadasHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        view.setOnClickListener(this)
        return FaltasJustificadasHolder(view)
    }

    override fun getItemCount(): Int {
        return justificarFaltasList.size
    }

    fun bindPackage(holder: FaltasJustificadasHolder, justificarFalta: JustificarFaltas){

        holder.alumnName?.text = justificarFalta.motivo
        holder.recyclerViewRellenar?.adapter= adapter
        holder.recyclerViewRellenar.visibility = View.GONE
        holder.buttonVer.visibility = View.GONE
        val square = ShapeDrawable(RectShape())
        if (estado == 0){
            val square = ShapeDrawable(RectShape())
            val color = ContextCompat.getColor(holder.itemView.context, R.color.pendiente)
            square.paint.color = color
            holder.imgCuadrado.background = square
        }else if(estado == 1){
            val square = ShapeDrawable(RectShape())
            val color = ContextCompat.getColor(holder.itemView.context, R.color.validado)
            square.paint.color = color
            holder.imgCuadrado.background = square

        }else if(estado == -1){
            val square = ShapeDrawable(RectShape())
            val color = ContextCompat.getColor(holder.itemView.context, R.color.rechazado)
            square.paint.color = color
            holder.imgCuadrado.background = square

        }



    }

    override fun onClick(view: View?) {
        clickListener?.onClick(view)
    }

    fun setOnClickListener(listener: View.OnClickListener){
        clickListener = listener
    }




}