package com.example.menulateral.ui.faltasJustificadas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.menulateral.DataModel.FaltaToShow
import com.example.menulateral.DataModel.Faltas
import com.example.menulateral.R

class UfColorRectangleAdapter( private val faltasList: MutableList<FaltaToShow>):
    RecyclerView.Adapter<UfColorRectangleAdapter.UfViewHolder>(),View.OnClickListener{

    private val layout = R.layout.item_uf_color // Reemplaza "nuevo_layout" con el nombre del nuevo layout que has proporcionado
    private var clickListener: View.OnClickListener? = null


    class UfViewHolder (val view: View): RecyclerView.ViewHolder(view){
        var hourAbsence: TextView
        var moduleNameAbsence: TextView
        var ufNameColorRectangle: TextView



        init {

            hourAbsence = view.findViewById(R.id.hourAbsenceColorRectangle)
            moduleNameAbsence = view.findViewById(R.id.moduleColorRectangle)
            ufNameColorRectangle = view.findViewById(R.id.ufNameColorRectangle)
        }
    }

    override fun onBindViewHolder(holder: UfColorRectangleAdapter.UfViewHolder, position: Int) {
        val uf = faltasList[position]
        bindPackage(holder, uf)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UfViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        view.setOnClickListener(this)

        return UfViewHolder(view)
    }

    override fun getItemCount(): Int {
        return faltasList.size
    }

    fun bindPackage(holder: UfViewHolder, falta: FaltaToShow){

        val horaInicio = if (falta.hora_inicio.length > 5) falta.hora_inicio.substring(0, 5) else falta.hora_inicio
        val horaFin = if (falta.hora_fin.length > 5) falta.hora_fin.substring(0, 5) else falta.hora_fin

        holder.hourAbsence?.text = horaInicio + "-" + horaFin
        holder.moduleNameAbsence?.text = falta.siglas_uf
        holder.ufNameColorRectangle?.text = falta.nombreUf

    }
    override fun onClick(view: View?) {
        clickListener?.onClick(view)
    }

    fun setOnClickListenerr(listener: View.OnClickListener) {
        clickListener = listener
    }



}