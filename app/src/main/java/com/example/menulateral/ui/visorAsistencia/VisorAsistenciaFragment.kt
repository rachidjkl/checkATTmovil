package com.example.menulateral.ui.visorAsistencia

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.menulateral.databinding.FragmentVisorAsistenciaBinding

class VisorAsistenciaFragment : Fragment() {

    private var _binding: FragmentVisorAsistenciaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVisorAsistenciaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //------------------------------------------------------PreogressBar-----------------------------------------------------------//
        binding.progressBar.clearAnimation();
        val currentProgres = 60000
        binding.progressBar.max = 10000;
        ObjectAnimator.ofInt( binding.progressBar, "progress", currentProgres)
            .setDuration(15000)
            .start()
        //-----------------------------------------------------------------------------------------------------------------------------//
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

