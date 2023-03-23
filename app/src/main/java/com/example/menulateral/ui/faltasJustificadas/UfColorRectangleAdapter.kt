package com.example.menulateral.ui.faltasJustificadas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.menulateral.R
import com.example.menulateral.Uf

class UfColorRectangleAdapter( private val ufList: MutableList<Uf>):
    RecyclerView.Adapter<UfColorRectangleAdapter.UfViewHolder>(){

    private val layout = R.layout.item_uf_color // Reemplaza "nuevo_layout" con el nombre del nuevo layout que has proporcionado


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
        val uf = ufList[position]
        bindPackage(holder, uf)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UfViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return UfViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ufList.size
    }

    fun bindPackage(holder: UfViewHolder, uf: Uf){

        holder.hourAbsence?.text = "10:40" + "-" + "11:40"
        holder.moduleNameAbsence?.text = "MO3"
        holder.ufNameColorRectangle?.text = uf.nombre_completo

    }



}