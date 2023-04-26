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
import com.example.menulateral.R
import com.example.menulateral.DataModel.UfConModulo
import java.math.RoundingMode
import java.text.DecimalFormat

class VisorAsistenciaAdapter(
    private val ufModulo: List<UfConModulo>):
    RecyclerView.Adapter<VisorAsistenciaAdapter.VisorAsistenciaHolder>(), View.OnClickListener {


    private val layout = R.layout.module_item // Reemplaza "nuevo_layout" con el nombre del nuevo layout que has proporcionado
    private var clickListener: View.OnClickListener? = null

    class VisorAsistenciaHolder(view: View) : RecyclerView.ViewHolder(view) {
        var moduloName: TextView
        var porcentaje: TextView
        var recyclerViewRellenar: RecyclerView
        var deployModule: ImageView
        var cardview: CardView


        init {
            moduloName = view.findViewById(R.id.moduleName)
            porcentaje = view.findViewById(R.id.porcentaje)
            recyclerViewRellenar = view.findViewById(R.id.recyclerUf)
            recyclerViewRellenar.layoutManager = LinearLayoutManager(view.context)
            deployModule = view.findViewById(R.id.deployModule)
            cardview = view.findViewById(R.id.cardView1)
        }
    }

    override fun onBindViewHolder(holder: VisorAsistenciaHolder, position: Int) {
        val uf = ufModulo[position]

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
        bindPackage(holder, uf)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisorAsistenciaHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        view.setOnClickListener(this)
        return VisorAsistenciaHolder(view)
    }

    override fun getItemCount(): Int {
        return ufModulo.size
    }

    fun bindPackage(holder: VisorAsistenciaHolder, uf: UfConModulo) {



        val adapter = AdapterAux(uf.ufs) //ADAPTER INTERNO DEL CARDVIEW

        holder.moduloName?.text = uf.nombreModulo
        holder.recyclerViewRellenar?.adapter = adapter
        holder.recyclerViewRellenar.visibility = View.GONE

        var sumaPorcentajes = 0
        var contador = 0
        uf.ufs.forEach { item ->

            if (item.listas_pasadas != 0) { // se verifica si item.listasPasadas es diferente a 0
                sumaPorcentajes += item.porcentaje_asistencia.toInt()
                contador++
                var porcentajeTotalModulo = if (sumaPorcentajes > contador) {
                    // Si la suma de porcentajes es mayor que el contador, asumimos que son porcentajes
                    (sumaPorcentajes / (contador.toFloat()*100)) * 100
                } else {
                    // Si la suma de porcentajes es menor o igual al contador, asumimos que son números decimales
                    (sumaPorcentajes / contador) * 100
                }

                val decimalFormat = DecimalFormat("#.#")
                decimalFormat.roundingMode = RoundingMode.HALF_UP
                val porcentajeTruncado = decimalFormat.format(porcentajeTotalModulo).toFloat()

                holder.porcentaje?.text = porcentajeTruncado.toString() + "%"
            }

        }





    }



    override fun onClick(view: View?) {
        clickListener?.onClick(view)
    }

    fun setOnClickListener(listener: View.OnClickListener) {
        clickListener = listener
    }
}