package com.example.menulateral.ui.faltasJustificadas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.menulateral.R
import com.example.menulateral.Uf
import com.example.menulateral.ui.Faltas

class FaltasJustificadasAdapter( val ufList: MutableList<Uf>, private val faltasList : MutableList<Faltas>):
    RecyclerView.Adapter<FaltasJustificadasAdapter.FaltasJustificadasHolder>(){
    val adapter = UfColorRectangleAdapter(ufList)

    private val layout = R.layout.adapter_item_color // Reemplaza "nuevo_layout" con el nombre del nuevo layout que has proporcionado

    class FaltasJustificadasHolder (val view: View): RecyclerView.ViewHolder(view){
        var alumnName: TextView
        var recyclerViewRellenar: RecyclerView



        init {
            alumnName = view.findViewById(R.id.alumnName)
            recyclerViewRellenar = view.findViewById(R.id.recyclerUfColorRectangle)
            recyclerViewRellenar.layoutManager = LinearLayoutManager(view.context)
        }
    }

    override fun onBindViewHolder(holder: FaltasJustificadasAdapter.FaltasJustificadasHolder, position: Int) {
        val falta = faltasList[position]
        bindPackage(holder, falta)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaltasJustificadasHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return FaltasJustificadasHolder(view)
    }

    override fun getItemCount(): Int {
        return faltasList.size
    }

    fun bindPackage(holder: FaltasJustificadasHolder, falta: Faltas){

        holder.alumnName?.text = falta.fecha
        holder.recyclerViewRellenar?.adapter= adapter

    }
}