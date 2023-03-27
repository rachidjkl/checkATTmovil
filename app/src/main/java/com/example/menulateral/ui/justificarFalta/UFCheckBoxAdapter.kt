package com.example.menulateral.ui.justificarFalta

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.menulateral.R
import com.example.menulateral.Uf

class UFCheckBoxAdapter(private val ufList: MutableList<Uf>, private val isChecked: Boolean):
    RecyclerView.Adapter<UFCheckBoxAdapter.UfViewHolder>(){

    private val layout = R.layout.item_uf_checkbox // Reemplaza "nuevo_layout" con el nombre del nuevo layout que has proporcionado
    private var clickListener: View.OnClickListener? = null

    class UfViewHolder (val view: View): RecyclerView.ViewHolder(view){
        var hourAbsence: TextView
        var moduleNameAbsence: TextView
        var ufNameCheckBox: TextView
        var checkBoxHourAbsence: CheckBox

        init {
            hourAbsence = view.findViewById(R.id.hourAbsence)
            moduleNameAbsence = view.findViewById(R.id.moduleNameAbsence)
            ufNameCheckBox = view.findViewById(R.id.ufNameCheckBox)
            checkBoxHourAbsence = view.findViewById(R.id.checkBoxHourAbsence)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UfViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return UfViewHolder(view)
    }

    override fun onBindViewHolder(holder: UfViewHolder, position: Int) {
        val uf = ufList[position]
        bindPackage(holder, uf)

        // Marca el checkbox al hacer clic en un elemento de la lista
        holder.itemView.setOnClickListener {
            holder.checkBoxHourAbsence.isChecked = !holder.checkBoxHourAbsence.isChecked
        }
    }

    override fun getItemCount(): Int {
        return ufList.size
    }

    fun bindPackage(holder: UfViewHolder, uf: Uf){

        holder.hourAbsence?.text = uf.horas_totales+ ":40 - " + uf.horas_cursadas+":00"
        holder.moduleNameAbsence?.text = "MO3"
        holder.ufNameCheckBox?.text = uf.nombre_completo
        holder.checkBoxHourAbsence.isChecked = isChecked

    }
}
