package com.example.menulateral.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menulateral.DataModel.Alumno
import com.example.menulateral.DataModel.Uf
import com.example.menulateral.databinding.FragmentGalleryBinding
import com.example.menulateral.ui.visorAsistencia.justificarFaltaAdapter

class GalleryFragment : Fragment() {

    val alumnoList = mutableListOf<Alumno>(
        Alumno(1, 101, "123", "Rachid", "Ghenem", "Arias","12",6,12,12,12,"12",2,2,),
        Alumno(2, 102, "124", "Marc", "Alzamora", "Tonto","12",6,12,1212,12,"12",12,12),
        Alumno(3, 103, "125", "Joel", "Mamagranao", "Tonto","12",6,12,12,12,"12",12,12)
    )


    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)

        val adapter = TuClaseAdapter(alumnoList)
        binding.RecyclerView.hasFixedSize()
        binding.RecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.RecyclerView.adapter = adapter

        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}