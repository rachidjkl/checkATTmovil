package com.example.menulateral.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menulateral.ApiAcces.ApiGets
import com.example.menulateral.ApiAcces.RetrofitClient
import com.example.menulateral.DataModel.Alumno
import com.example.menulateral.DataModel.FaltaJustificada2
import com.example.menulateral.DataModel.FaltaToShow
import com.example.menulateral.Login
import com.example.menulateral.R
import com.example.menulateral.databinding.FragmentSlideshowBinding
import com.example.menulateral.extension.extensionFaltasJustificadas
import com.example.menulateral.ui.faltasJustificadas.FaltasJustificadasAdapter
import com.example.menulateral.ui.slideshow.extensiones.ValidarFragmenExtensionA
import com.example.menulateral.ui.visorAsistencia.AdapterValidarJustificante
import com.example.menulateral.ui.visorAsistencia.VisorAsistenciaAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null

    private val binding get() = _binding!!

    private var listaAlumnos : MutableList<Alumno>? = null
    private var numFaltasPendientes : Int? = null

    init {
        main()
    }

    fun main() = runBlocking {
        listaAlumnos = getAlumnos()
        numFaltasPendientes = getNumFaltas()
    }



    fun onItemClick(alumno: Alumno) {
        //-------------------------------------------------Cambiar fragment-------------------------------------------//
        val fragmentoPasarLista = ValidarFragmenExtensionA(alumno)
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        // Agregar el fragmento actual a la pila de fragmentos
        // Reemplazar el fragmento actual con el FragmentNuevo
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main2, fragmentoPasarLista)
        fragmentTransaction.commit()

        //------------------------------------------------------------------------------------------------------------//
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        numFaltasPendientes = (numFaltasPendientes?.minus(9))
        binding.text.text= numFaltasPendientes.toString()
        val adapter = AdapterValidarJustificante(this, listaAlumnos)
        binding.RecyclerView.hasFixedSize()
        binding.RecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.RecyclerView.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun getAlumnos(): MutableList<Alumno>? {

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

        return GlobalScope.async {
            val call = userCepApi.getAlumnosList(Login.profe.idProfe)
            val response = call.execute()
            response.body()
        }.await()
    }

    private suspend fun getNumFaltas(): Int? {

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

        return GlobalScope.async {
            val call = userCepApi.numFaltasPendientes(0)
            val response = call.execute()
            response.body()
        }.await()
    }
}