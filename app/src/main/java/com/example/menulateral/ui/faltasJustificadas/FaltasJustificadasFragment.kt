package com.example.menulateral.ui.faltasJustificadas

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.menulateral.R
import com.example.menulateral.databinding.FragmentFaltasJustificadasBinding

class FaltasJustificadasFragment : Fragment() {

    private var _binding: FragmentFaltasJustificadasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}