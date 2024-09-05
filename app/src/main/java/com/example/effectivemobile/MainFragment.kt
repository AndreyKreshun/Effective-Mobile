package com.example.effectivemobile

import OffersAdapter
import VacancyAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.effectivemobile.data.JsonResponse
import com.example.effectivemobile.databinding.FragmentMainBinding
import com.google.gson.Gson
import java.io.InputStreamReader

class MainFragment : Fragment() {

    // Используем View Binding
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Чтение JSON из assets
        val jsonString = readJsonFromAssets("mock_json.json")
        val jsonResponse: JsonResponse = Gson().fromJson(jsonString, JsonResponse::class.java)

        // Настройка RecyclerView для списка предложений и вакансий
        setupRecyclerViews(jsonResponse)
        setupUI(jsonResponse)
    }

    private fun readJsonFromAssets(fileName: String): String {
        val inputStream = requireContext().assets.open(fileName)
        val reader = InputStreamReader(inputStream)
        return reader.readText().also { reader.close() }
    }

    private fun setupRecyclerViews(jsonResponse: JsonResponse) {
        // Настройка адаптера для предложений
        val recommendationsAdapter = OffersAdapter(jsonResponse.offers)
        binding.recyclerViewOffers.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewOffers.adapter = recommendationsAdapter

        // Настройка адаптера для вакансий
        val vacanciesAdapter = VacancyAdapter(jsonResponse.vacancies.toMutableList())
        binding.recyclerViewVacancies.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewVacancies.adapter = vacanciesAdapter
    }

    private fun setupUI(jsonResponse: JsonResponse) {
        val totalVacancies = jsonResponse.vacancies.size
        val visibleVacanciesCount = 3
        var currentVisibleVacancies = visibleVacanciesCount

        // Показ первых вакансий
        val vacanciesAdapter = VacancyAdapter(jsonResponse.vacancies.take(currentVisibleVacancies).toMutableList())
        binding.recyclerViewVacancies.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewVacancies.adapter = vacanciesAdapter

        if (totalVacancies > visibleVacanciesCount) {
            // Показать кнопку "Еще вакансии" если вакансий больше, чем отображаем
            binding.buttonMoreVacancies.visibility = View.VISIBLE
            binding.buttonMoreVacancies.text = "Еще $totalVacancies вакансий"

            binding.buttonMoreVacancies.setOnClickListener {
                currentVisibleVacancies = totalVacancies
                vacanciesAdapter.updateData(jsonResponse.vacancies.toMutableList())
                binding.recyclerViewVacancies.scrollToPosition(visibleVacanciesCount)
                binding.buttonMoreVacancies.visibility = View.GONE
            }
        } else {
            binding.buttonMoreVacancies.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
