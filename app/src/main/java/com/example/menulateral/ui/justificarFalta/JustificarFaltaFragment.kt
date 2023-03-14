package com.example.menulateral.ui.justificarFalta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.menulateral.databinding.FragmentJustificarFaltaBinding

class JustificarFaltaFragment : Fragment() {

    private var _binding: FragmentJustificarFaltaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(JustificarFaltaViewModel::class.java)

        _binding = FragmentJustificarFaltaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val stringList = listOf(
            "Lorem ipsum dolor sit amet",
            "Consectetur adipiscing elit",
            "Sed do eiusmod tempor incididunt",
            "Ut labore et dolore magna aliqua",
            "Ut enim ad minim veniam",
            "Quis nostrud exercitation ullamco",
            "Laboris nisi ut aliquip ex ea commodo consequat",
            "Duis aute irure dolor in reprehenderit in voluptate velit",
            "Esse cillum dolore eu fugiat nulla pariatur",
            "Excepteur sint occaecat cupidatat non proident",
            "Sunt in culpa qui officia deserunt mollit anim id est laborum",
            "Nemo enim ipsam voluptatem quia voluptas sit aspernatur",
            "Aut odit aut fugit",
            "Sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt",
            "Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet",
            "Consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt"
        )

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, stringList)
        binding.recyclerView2.adapter = adapter

        val textView: TextView = binding.textReason
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
