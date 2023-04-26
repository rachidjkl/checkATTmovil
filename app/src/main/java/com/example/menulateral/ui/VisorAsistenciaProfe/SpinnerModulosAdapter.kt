package com.example.menulateral.ui.VisorAsistenciaProfe

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.menulateral.DataModel.ModuloClase

class SpinnerModulosAdapter(context: Context, resource: Int, objects: List<ModuloClase>) :
    ArrayAdapter<ModuloClase>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = getItem(position)?.siglasModulo + " - " + getItem(position)?.nombreModulo
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = getItem(position)?.siglasModulo + " - " + getItem(position)?.nombreModulo
        return view
    }
}