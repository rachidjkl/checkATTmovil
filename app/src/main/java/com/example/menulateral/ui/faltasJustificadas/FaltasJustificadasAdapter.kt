package com.example.menulateral.ui.faltasJustificadas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.menulateral.R
import com.example.menulateral.Uf

class FaltasJustificadasAdapter( private val ufList: MutableList<Uf>):
    RecyclerView.Adapter<FaltasJustificadasAdapter.UfViewHolder>(){
    val adapter = UfColorRectangleAdapter(ufList)

    private val layout = R.layout.adapter_item_color // Reemplaza "nuevo_layout" con el nombre del nuevo layout que has proporcionado
    private var clickListener: View.OnClickListener? = null

    class UfViewHolder (val view: View): RecyclerView.ViewHolder(view){
        var alumnName: TextView
        /*var recyclerViewRellenar: RecyclerView*/



        init {
            alumnName = view.findViewById(R.id.alumnName)
            /*recyclerViewRellenar = view.findViewById(R.id.recyclerUfColorRectangle)*/
        }
    }

    override fun onBindViewHolder(holder: UfViewHolder, position: Int) {
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

        holder.alumnName?.text = "12/04/23"
       /* holder.recyclerViewRellenar.adapter= adapter*/

    }


}