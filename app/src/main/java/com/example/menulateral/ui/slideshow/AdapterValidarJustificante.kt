package com.example.menulateral.ui.visorAsistencia


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.menulateral.ApiAcces.ApiGets
import com.example.menulateral.ApiAcces.RetrofitClient
import com.example.menulateral.DataModel.Alumno
import com.example.menulateral.DataModel.FaltaJustificada2
import com.example.menulateral.DataModel.FaltaToShow
import com.example.menulateral.R
import com.example.menulateral.ui.slideshow.SlideshowFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class AdapterValidarJustificante (private val listener: SlideshowFragment,private val alumnoList: MutableList<Alumno>?):
    RecyclerView.Adapter<AdapterValidarJustificante.AdapterValidarJustificanteHolder>(), View.OnClickListener {


    private val layout = R.layout.card_view_validar_faltas
    private var clickListener: View.OnClickListener? = null
    private var faltaJustificadaListPendiente: MutableList<FaltaJustificada2>? = null


    fun main(alumno: Alumno) = runBlocking {
        faltaJustificadaListPendiente = cargarListFaltaJustificada(alumno.idAlumno, 0)

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class AdapterValidarJustificanteHolder(view: View) : RecyclerView.ViewHolder(view) {
        var alumName: TextView
        var porcentaje: TextView
        var cardView: CardView
        var circulo: ImageView


        init {
            circulo = view.findViewById(R.id.circulo)
            porcentaje = view.findViewById(R.id.porcentaje)
            alumName = view.findViewById(R.id.alumnName)
            cardView = view.findViewById(R.id.cardView1)
        }
    }

    override fun onBindViewHolder(holder: AdapterValidarJustificanteHolder, position: Int) {
        val alumno = alumnoList?.get(position)
        holder.cardView.setOnClickListener(){
            if (alumno != null) {
                listener.onItemClick(alumno)
            }
        }

        if (alumno != null) {
            bindPackage(holder, alumno)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterValidarJustificanteHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        view.setOnClickListener(this)
        return AdapterValidarJustificanteHolder(view)
    }

    override fun getItemCount(): Int {
        return alumnoList!!.size
    }

    fun bindPackage(holder: AdapterValidarJustificanteHolder, alumno: Alumno) {
        main(alumno)
        holder.porcentaje.text = faltaJustificadaListPendiente?.size.toString()
        if (faltaJustificadaListPendiente?.size == null ){
            holder.circulo.setBackgroundResource(R.drawable.circulo_verde)
            holder.porcentaje.text = "0"
        }
        holder.alumName?.text = alumno.nombreAlumno+" "+alumno.apellido1Alumno
    }



    override fun onClick(view: View?) {
        clickListener?.onClick(view)
    }

    fun setOnClickListener(listener: View.OnClickListener) {
        clickListener = listener
    }

    suspend fun cargarListFaltaJustificada(alumno : Int, estado : Int):MutableList<FaltaJustificada2>?{

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

        return GlobalScope.async {
            val call = userCepApi.getFaltaJustificada(alumno,estado)
            val response = call.execute()
            response.body()
        }.await()
    }
}