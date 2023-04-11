package com.example.menulateral.ui.justificarFalta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.menulateral.DataModel.FaltaToShow
import com.example.menulateral.R
import com.example.menulateral.DataModel.Uf

class UFCheckBoxAdapter(private val faltas: List<FaltaToShow>, private val isChecked: Boolean):
    RecyclerView.Adapter<UFCheckBoxAdapter.UfViewHolder>(){

    private val layout = R.layout.item_ver_faltas // especificamos en layout
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

        holder.hourAbsence?.text = falta.hora_inicio+ "-" + falta.hora_fin
        holder.moduleNameAbsence?.text = falta.siglas_uf
        holder.ufNameCheckBox?.text = falta.nombreUf

    }
}
