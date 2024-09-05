package com.example.effectivemobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.effectivemobile.databinding.FragmentApplyBinding

class ApplyFragment : DialogFragment() {

    private var _binding: FragmentApplyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApplyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Логика отображения поля для сопроводительного письма
        binding.buttonAddCoverLetter.setOnClickListener {
            if (binding.coverLetterInput.visibility == View.GONE) {
                binding.coverLetterInput.visibility = View.VISIBLE
            } else {
                binding.coverLetterInput.visibility = View.GONE
            }
        }

        // Логика при нажатии на кнопку "Откликнуться"
        binding.buttonApply.setOnClickListener {
            dismiss() // Закрытие модального окна
            Toast.makeText(requireContext(), "Вы откликнулись на вакансию", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
