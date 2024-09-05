package com.example.effectivemobile

import VacancyAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.effectivemobile.data.Vacancy
import com.example.effectivemobile.databinding.FragmentFavoritesBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoriteVacanciesAdapter: VacancyAdapter
    private var favoriteVacancies: List<Vacancy> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Настройка RecyclerView
        setupRecyclerView()

        // Загрузка данных
        loadFavoriteVacancies()
    }

    override fun onResume() {
        super.onResume()
        // Обновляем данные при возвращении на экран
        loadFavoriteVacancies()
    }

    private fun loadFavoriteVacancies() {
        // Получаем избранные вакансии
        favoriteVacancies = getFavoriteVacanciesFromDataSource()

        // Обновляем адаптер
        //favoriteVacanciesAdapter.submit(favoriteVacancies)

        // Обновляем текст с количеством избранных вакансий
        updateFavoritesCount()

        // Если нет избранных вакансий, показываем сообщение
        if (favoriteVacancies.isEmpty()) {
            binding.recyclerViewFavorites.visibility = View.GONE
        } else {
            binding.recyclerViewFavorites.visibility = View.VISIBLE
        }
    }

    private fun getFavoriteVacanciesFromDataSource(): List<Vacancy> {
        val inputStream = requireContext().assets.open("mock_json.json")
        val json = InputStreamReader(inputStream).use { it.readText() }

        // Парсим JSON с помощью Gson
        val gson = Gson()
        val vacancyType = object : TypeToken<List<Vacancy>>() {}.type

        // Преобразуем json в список вакансий
        val vacancies: List<Vacancy> = gson.fromJson(json, vacancyType)

        // Возвращаем список только избранных вакансий
        return vacancies.filter { it.isFavorite }
    }


    private fun setupRecyclerView() {
        favoriteVacanciesAdapter = VacancyAdapter(favoriteVacancies.toMutableList())
        binding.recyclerViewFavorites.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favoriteVacanciesAdapter
        }
    }

    private fun updateFavoritesCount() {
        val count = favoriteVacancies.size
        binding.textViewFavoritesCount.text = getString(R.string.favorites_count, count, getCorrectWordForm(count))
    }

    /*private fun getFavoriteVacanciesFromDataSource(): List<Vacancy> {
        // Пример получения данных. Замените это на реальный источник данных.
        return DummyData.vacancies.filter { it.isFavorite }
    }*/

    private fun getCorrectWordForm(count: Int): String {
        return when {
            count % 10 == 1 && count % 100 != 11 -> "вакансия"
            count % 10 in 2..4 && (count % 100 !in 12..14) -> "вакансии"
            else -> "вакансий"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
