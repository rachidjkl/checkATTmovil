package com.example.menulateral.ui.visorAsistencia

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        val porcentajeAlumno = 80
        binding.progressBar.max = 100;
        binding.progressBar.setProgress(0)
        var cont = 0;
        val timer = object: CountDownTimer(15000, 5) {
            override fun onTick(millisUntilFinished: Long) {
                if (cont < porcentajeAlumno){
                    var aux = binding.textHome.text.toString()
                    binding.textHome.text = (aux.toInt() + 1).toString()
                    binding.progressBar.setProgress(binding.progressBar.progress +1)
                    cont ++
                }
            }
            override fun onFinish() {

                binding.textHome.text = porcentajeAlumno.toString()
                binding.progressBar.setProgress(porcentajeAlumno)

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

