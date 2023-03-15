package com.example.menulateral.ui.visorAsistencia

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
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

        binding.progressBar.max = 100;
        binding.progressBar.setProgress(0)
        var cont = 0;
        val timer = object: CountDownTimer(1500, 15) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                if (cont < 50){
                    var aux = binding.textHome.text.toString()
                    binding.textHome.text = (aux.toInt() + 1).toString()
                    binding.progressBar.setProgress(binding.progressBar.progress +1)
                    cont ++
                }
            }
            override fun onFinish() {
                binding.textHome.text = "50"
                binding.progressBar.setProgress(50)

            }
        }
        timer.start()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

