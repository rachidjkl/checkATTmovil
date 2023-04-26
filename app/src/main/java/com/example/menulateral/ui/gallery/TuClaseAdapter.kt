package com.example.menulateral.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.menulateral.ApiAcces.ApiGets
import com.example.menulateral.ApiAcces.RetrofitClient
import com.example.menulateral.DataModel.Alumno
import com.example.menulateral.DataModel.ModuloUFVisorAsistencia
import com.example.menulateral.Login
import com.example.menulateral.R
import com.example.menulateral.ui.visorAsistencia.VisorAsistenciaFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class TuClaseAdapter (val AlumnosList : MutableList<Alumno>?, val fragment: Fragment):
    RecyclerView.Adapter<TuClaseAdapter.TuClaseHolder>(), View.OnClickListener {

    private val layout = R.layout.card_view_alumno // Reemplaza "nuevo_layout" con el nombre del nuevo layout que has proporcionado
    private var clickListener: View.OnClickListener? = null
    private var porcentajeTotal: List<ModuloUFVisorAsistencia>? = null
    var porcientoTotal = 0f


    class TuClaseHolder(view: View) : RecyclerView.ViewHolder(view) {
        var alumnName: TextView
        var porcentajetxt: TextView
        var cardview: CardView


        init {
            alumnName = view.findViewById(R.id.alumnName)
            cardview = view.findViewById(R.id.cardView1)
            porcentajetxt = view.findViewById(R.id.porcentaje)

        }



    }

    fun main(idAlumno: Int) = runBlocking {
        porcentajeTotal = globalFun(idAlumno)
    }

    override fun onBindViewHolder(holder: TuClaseHolder, position: Int) {
        val alumno = AlumnosList?.get(position)
        holder.cardview.setOnClickListener() {

            val bundle = Bundle()
            bundle.putSerializable("alumno", AlumnosList?.get(position))
            val visorAsistencia = VisorAsistenciaFragment()
            visorAsistencia.arguments = bundle
            val fragmentManager = fragment.requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            // Agregar el fragmento actual a la pila de fragmentos
            fragmentTransaction.addToBackStack(null)

            // Reemplazar el fragmento actual con el FragmentListasPasadas2
            fragmentTransaction.replace(R.id.nav_host_fragment_content_main2, visorAsistencia)
            fragmentTransaction.commit()
        }
        if (alumno != null) {
            bindPackage(holder, alumno)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TuClaseHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        view.setOnClickListener(this)
        return TuClaseHolder(view)
    }

    override fun getItemCount(): Int {
        return AlumnosList!!.size
    }

    fun bindPackage(holder: TuClaseHolder, alumno: Alumno) {

        holder.alumnName?.text = alumno.nombreAlumno+" "+alumno.apellido1Alumno+" "
        main(alumno.idAlumno)
        var count = 0
        var values = 0f
        for (listaUf in porcentajeTotal!!){
            if (listaUf.porcentaje_asistencia != 0.0f){
                values += listaUf.porcentaje_asistencia
                count++
            }
        }
        var result = values/count
        porcientoTotal += result
        holder.porcentajetxt.text = result.toString()
    }

    override fun onClick(view: View?) {
        clickListener?.onClick(view)
    }

    fun setOnClickListener(listener: View.OnClickListener) {
        clickListener = listener
    }

    private suspend fun globalFun(idAlumno: Int): List<ModuloUFVisorAsistencia>? {

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

        return GlobalScope.async {
            val call = userCepApi.getVisorAistencia(idAlumno)
            val response = call.execute()
            response.body()
        }.await()
    }
}