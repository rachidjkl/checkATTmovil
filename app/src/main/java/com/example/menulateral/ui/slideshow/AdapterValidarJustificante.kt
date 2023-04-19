package com.example.menulateral.ui.visorAsistencia


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.menulateral.DataModel.Alumno
import com.example.menulateral.DataModel.FaltaToShow
import com.example.menulateral.R
import com.example.menulateral.ui.slideshow.SlideshowFragment

class AdapterValidarJustificante (private val listener: SlideshowFragment,private val alumnoList: List<Alumno>):
    RecyclerView.Adapter<AdapterValidarJustificante.AdapterValidarJustificanteHolder>(), View.OnClickListener {


    private val layout = R.layout.card_view_validar_faltas
    private var clickListener: View.OnClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class AdapterValidarJustificanteHolder(view: View) : RecyclerView.ViewHolder(view) {
        var alumName: TextView
        var cardView: CardView


        init {
            alumName = view.findViewById(R.id.alumnName)
            cardView = view.findViewById(R.id.cardView1)
        }
    }

    override fun onBindViewHolder(holder: AdapterValidarJustificanteHolder, position: Int) {
        val alumno = alumnoList[position]
        holder.cardView.setOnClickListener(){
            listener.onItemClick(alumno)
        }

        bindPackage(holder, alumno)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterValidarJustificanteHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        view.setOnClickListener(this)
        return AdapterValidarJustificanteHolder(view)
    }

    override fun getItemCount(): Int {
        return alumnoList.size
    }

    fun bindPackage(holder: AdapterValidarJustificanteHolder, alumno: Alumno) {

        holder.alumName?.text = alumno.nombreAlumno+" "+alumno.apellido1Alumno
    }



    override fun onClick(view: View?) {
        clickListener?.onClick(view)
    }

    fun setOnClickListener(listener: View.OnClickListener) {
        clickListener = listener
    }
}