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
import com.example.menulateral.JustificarFaltas
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


    val faltasList = mutableListOf<Faltas>(
        Faltas(1, 101, 1, "09:00"),
        Faltas(2, 102, 1, "11:00"),
        Faltas(3, 103, 1, "12:00"),
        Faltas(4, 101, 2, "13:00"),
        Faltas(5, 102, 2, "14:00"),
        Faltas(6, 103, 2, "08:00")
    )

    val justificarFaltasList = mutableListOf<JustificarFaltas>(
        JustificarFaltas(1, "Medico", "123", "UF1 - Introducción a la programación", "08"),
        JustificarFaltas(2, "Pedo", "124", "UF2 - Programación orientada a objetos", "09"),
        JustificarFaltas(3, "Examen Conducir", "125", "UF3 - Estructuras de datos y algoritmos", "10"),
        JustificarFaltas(4, "Sida", "126", "UF4 - Bases de datos y SQL", "11")
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
                    val adapter = FaltasJustificadasAdapter(faltasList, justificarFaltasList, 0)
                    binding.RecyclerView.hasFixedSize()
                    binding.RecyclerView.layoutManager = LinearLayoutManager(this.context)
                    binding.RecyclerView.adapter = adapter
                }
                1 -> {
                    val adapter = FaltasJustificadasAdapter(faltasList, justificarFaltasList, 1)
                    binding.RecyclerView.hasFixedSize()
                    binding.RecyclerView.layoutManager = LinearLayoutManager(this.context)
                    binding.RecyclerView.adapter = adapter
                }
                2 -> {
                    val adapter = FaltasJustificadasAdapter(faltasList, justificarFaltasList, -1)
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
                        val adapter = FaltasJustificadasAdapter(faltasList, justificarFaltasList, 0)
                        binding.RecyclerView.hasFixedSize()
                        binding.RecyclerView.layoutManager = LinearLayoutManager(context)
                        binding.RecyclerView.adapter = adapter
                    }
                    1 -> {
                        val adapter = FaltasJustificadasAdapter(faltasList, justificarFaltasList, 1)
                        binding.RecyclerView.hasFixedSize()
                        binding.RecyclerView.layoutManager = LinearLayoutManager(context)
                        binding.RecyclerView.adapter = adapter
                    }
                    2 -> {
                        val adapter = FaltasJustificadasAdapter(faltasList, justificarFaltasList, -1)
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