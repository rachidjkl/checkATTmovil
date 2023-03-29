package com.example.menulateral.ui.faltasJustificadas

import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menulateral.Faltas
import com.example.menulateral.R
import com.example.menulateral.Uf
import com.example.menulateral.databinding.AdapterItemColorBinding
import com.example.menulateral.databinding.FragmentFaltasJustificadasBinding
import com.google.android.material.tabs.TabLayout

class FaltasJustificadasFragment : Fragment() {

    private var _binding: FragmentFaltasJustificadasBinding? = null
    private var _bindingUf: AdapterItemColorBinding? = null

    private val bindingUf get() = _bindingUf!!

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    val faltasList = mutableListOf<Faltas>(//
        Faltas(1, 101, "12/03/23", "Medico", "08", "V", -1),
        Faltas(2, 102, "02/03/23", "Trabajo", "09", "P",-1),
        Faltas(3, 103, "31/03/23", "Trabajo", "10", "R",1),
        Faltas(4, 101, "07/03/23", "Examen onducir", "11", "V",1),
        Faltas(5, 102, "24/05/23", "Bar", "12", "V",0),
        Faltas(6, 103, "12/04/23", "Sida", "13", "V",0)
    )

    val ufList = mutableListOf<Uf>(
        Uf(1, 101, 123, "UF1 - Introducción a la programación", "08", "09"),
        Uf(2, 102, 124, "UF2 - Programación orientada a objetos", "09", "10"),
        Uf(3, 103, 125, "UF3 - Estructuras de datos y algoritmos", "10", "11"),
        Uf(4, 101, 126, "UF4 - Bases de datos y SQL", "11", "12"),
        Uf(5, 102, 127, "UF5 - Desarrollo web con JavaScript", "12", "13"),
        Uf(6, 103, 128, "UF6 - Desarrollo móvil con Kotlin", "13", "14")
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
        //crear una clase para que pueda usar la lista de cards
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        // Encuentra la vista por su ID

        // Crea un ShapeDrawable en forma de cuadrado
        val square = ShapeDrawable(RectShape())





        //Se muestra este tab seleccionado por defecto
        val defaultTab = binding.tabs.getTabAt(0)
        defaultTab?.select()

        defaultTab?.let {
            val position = it.position
            when (position) {
                0 -> {
                    val adapter = FaltasJustificadasAdapter(ufList, faltasList, 0)
                    binding.RecyclerView.hasFixedSize()
                    binding.RecyclerView.layoutManager = LinearLayoutManager(this.context)
                    binding.RecyclerView.adapter = adapter
                }
                1 -> {
                    val adapter = FaltasJustificadasAdapter(ufList, faltasList, 1)
                    binding.RecyclerView.hasFixedSize()
                    binding.RecyclerView.layoutManager = LinearLayoutManager(this.context)
                    binding.RecyclerView.adapter = adapter
                }
                2 -> {
                    val adapter = FaltasJustificadasAdapter(ufList, faltasList, -1)
                    binding.RecyclerView.hasFixedSize()
                    binding.RecyclerView.layoutManager = LinearLayoutManager(this.context)
                    binding.RecyclerView.adapter = adapter
                }
            }
        }

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                var sel0 = false
                var sel1 = false
                var sel2 = false
                when (position) {
                    0 -> {
                        val adapter = FaltasJustificadasAdapter(ufList, faltasList, 0)
                        binding.RecyclerView.hasFixedSize()
                        binding.RecyclerView.layoutManager = LinearLayoutManager(context)
                        binding.RecyclerView.adapter = adapter
                    }
                    1 -> {
                        val adapter = FaltasJustificadasAdapter(ufList, faltasList, 1)
                        binding.RecyclerView.hasFixedSize()
                        binding.RecyclerView.layoutManager = LinearLayoutManager(context)
                        binding.RecyclerView.adapter = adapter
                    }
                    2 -> {
                        val adapter = FaltasJustificadasAdapter(ufList, faltasList, -1)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}