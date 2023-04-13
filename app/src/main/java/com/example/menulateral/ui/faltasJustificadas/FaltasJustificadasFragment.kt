package com.example.menulateral.ui.faltasJustificadas

import android.graphics.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menulateral.ApiAcces.ApiGets
import com.example.menulateral.ApiAcces.RetrofitClient
import com.example.menulateral.DataModel.Faltas
import com.example.menulateral.DataModel.FaltaJustificada
import com.example.menulateral.DataModel.FaltaToShow
import com.example.menulateral.Login
import com.example.menulateral.R
import com.example.menulateral.databinding.FragmentFaltasJustificadasBinding
import com.example.menulateral.extension.extensionFaltasJustificadas
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FaltasJustificadasFragment : Fragment() {

    private var _binding: FragmentFaltasJustificadasBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    fun onItemClick(position: Int) {
        //-------------------------------------------------Cambiar fragment-------------------------------------------//
        val fragmentoPasarLista = extensionFaltasJustificadas()
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        // Agregar el fragmento actual a la pila de fragmentos
        // Reemplazar el fragmento actual con el FragmentNuevo
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, fragmentoPasarLista)
        fragmentTransaction.commit()

        //------------------------------------------------------------------------------------------------------------//
    }


    val faltasList = mutableListOf<Faltas>(
        Faltas(1, 101, 1, "09:00"),
        Faltas(2, 102, 1, "11:00"),
        Faltas(3, 103, 1, "12:00"),
        Faltas(4, 101, 2, "13:00"),
        Faltas(5, 102, 2, "14:00"),
        Faltas(6, 103, 2, "08:00")
    )

    val faltaJustificadaList = mutableListOf<FaltaJustificada>(
        FaltaJustificada(1, "Medico", "123", "UF1 - Introducción a la programación", 8),
        FaltaJustificada(2, "Pedo", "124", "UF2 - Programación orientada a objetos", 9),
        FaltaJustificada(3, "Examen Conducir", "125", "UF3 - Estructuras de datos y algoritmos", 10),
        FaltaJustificada(4, "Sida", "126", "UF4 - Bases de datos y SQL", 11)
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val faltasJustificadasViewModel =
            ViewModelProvider(this).get(FaltasJustificadasViewModel::class.java)

        _binding = FragmentFaltasJustificadasBinding.inflate(inflater, container, false)

        val modulos = resources.getStringArray(R.array.spinner_options)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, modulos)
        binding.autoCompleteTextView6.setAdapter(arrayAdapter)


        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mmg = cargarListFaltaJustificada(20001, 1)

        val adapter = FaltasJustificadasAdapter(this@FaltasJustificadasFragment,faltasList, mmg, 0)
        binding.RecyclerView.hasFixedSize()
        binding.RecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.RecyclerView.adapter = adapter
        adapter

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.alumno_ideal) // Cargar imagen desde drawable
        val diameter = 200 // Diámetro del círculo

        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, diameter, diameter, true) // Escalar imagen al tamaño del círculo

        val circleBitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888) // Crear nuevo bitmap para el círculo
        val canvas = Canvas(circleBitmap) // Dibujar en el nuevo bitmap
        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = Color.WHITE
        canvas.drawCircle(diameter / 2f, diameter / 2f, diameter / 2f, paint) // Dibujar círculo blanco
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(scaledBitmap, 0f, 0f, paint) // Dibujar imagen en el círculo

        binding.imageView.setImageBitmap(circleBitmap) // Mostrar el bitmap del círculo en el ImageView

        binding.tabs.setTabTextColors(Color.BLACK, resources.getColor(R.color.azul))
        binding.tabs.setSelectedTabIndicatorColor(resources.getColor(R.color.azul))
        binding.tabs.setBackgroundColor(resources.getColor(R.color.fondo))

        binding.tabs.addTab(binding.tabs.newTab().setText("Pendientes"))
        binding.tabs.addTab(binding.tabs.newTab().setText("Validadas"))
        binding.tabs.addTab(binding.tabs.newTab().setText("Rechazadas"))

        //Se muestra este tab seleccionado por defecto
        val defaultTab = binding.tabs.getTabAt(0)
        defaultTab?.select()

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                when (position) {
                    0 -> {
                        val adapter = FaltasJustificadasAdapter(this@FaltasJustificadasFragment,faltasList, cargarListFaltaJustificada(Login.alumno.idAlumno, 0), 0)
                        binding.RecyclerView.hasFixedSize()
                        binding.RecyclerView.layoutManager = LinearLayoutManager(context)
                        binding.RecyclerView.adapter = adapter
                    }
                    1 -> {
                        val adapter = FaltasJustificadasAdapter(this@FaltasJustificadasFragment,faltasList, cargarListFaltaJustificada(Login.alumno.idAlumno, 1), 1)
                        binding.RecyclerView.hasFixedSize()
                        binding.RecyclerView.layoutManager = LinearLayoutManager(context)
                        binding.RecyclerView.adapter = adapter
                    }
                    2 -> {
                        val adapter = FaltasJustificadasAdapter(this@FaltasJustificadasFragment,faltasList, cargarListFaltaJustificada(Login.alumno.idAlumno, -1), -1)
                        binding.RecyclerView.hasFixedSize()
                        binding.RecyclerView.layoutManager = LinearLayoutManager(context)
                        binding.RecyclerView.adapter = adapter
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }


    fun cargarListFaltaJustificada(alumno : Int, estado : Int):MutableList<FaltaJustificada>?{

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)
        var localUserCep: MutableList<FaltaJustificada>? = null

        GlobalScope.launch() {

            val action = async {
                val call = userCepApi.getFaltaJustificada(alumno, estado)
                val response = call.execute();
                localUserCep = response.body() as MutableList<FaltaJustificada>?
            }
            action.await()
        }
        return localUserCep;


    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}
