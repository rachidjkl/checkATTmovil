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

<<<<<<< HEAD
    private val layout = R.layout.item_ver_faltas // especificamos en layout
=======
    companion object{
        var selectedFaltas = mutableListOf<FaltaToShow>()
    }


    private val layout = R.layout.item_uf_checkbox // especificamos en layout
>>>>>>> JoelBranch1
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

<<<<<<< HEAD
=======
        // Marca el checkbox al hacer clic en un elemento de la lista
        holder.itemView.setOnClickListener {
            holder.checkBoxHourAbsence.isChecked = !holder.checkBoxHourAbsence.isChecked
        }
        // Agrega o quita el objeto seleccionado de la lista según el estado del CheckBox
        holder.checkBoxHourAbsence.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedFaltas.add(falta)
            } else {
                selectedFaltas.remove(falta)
            }
        }
>>>>>>> JoelBranch1
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
<<<<<<< HEAD

=======
        holder.checkBoxHourAbsence.isChecked = isChecked
>>>>>>> JoelBranch1
    }

}
