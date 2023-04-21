package com.example.menulateral.ui.justificarFalta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.menulateral.DataModel.FaltaToShow
import com.example.menulateral.R

class UFCheckBoxAdapter(private val faltas: List<FaltaToShow>, private val isChecked: Boolean):
    RecyclerView.Adapter<UFCheckBoxAdapter.UfViewHolder>(){

    companion object{
        var selectedFaltas = mutableListOf<FaltaToShow>()
    }


    private val layout = R.layout.item_uf_checkbox2 // especificamos en layout
    private var clickListener: View.OnClickListener? = null

    class UfViewHolder (val view: View): RecyclerView.ViewHolder(view){
        var hourAbsence: TextView
        var moduleNameAbsence: TextView
        var ufNameCheckBox: TextView


        init {
            hourAbsence = view.findViewById(R.id.hourAbsence)
            moduleNameAbsence = view.findViewById(R.id.moduleNameAbsence)
            ufNameCheckBox = view.findViewById(R.id.ufNameCheckBox)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UfViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return UfViewHolder(view)
    }

    override fun onBindViewHolder(holder: UfViewHolder, position: Int) {
        val falta = faltas[position]

        bindPackage(holder, falta)


    }

    override fun getItemCount(): Int {
        return faltas.size
    }

    fun bindPackage(holder: UfViewHolder, falta: FaltaToShow){

        val horaInicio = if (falta.hora_inicio.length > 5) falta.hora_inicio.substring(0, 5) else falta.hora_inicio
        val horaFin = if (falta.hora_fin.length > 5) falta.hora_fin.substring(0, 5) else falta.hora_fin

        holder.hourAbsence?.text = horaInicio + "-" + horaFin
        holder.moduleNameAbsence?.text = falta.siglas_uf
        holder.ufNameCheckBox?.text = falta.nombreUf

    }

}
