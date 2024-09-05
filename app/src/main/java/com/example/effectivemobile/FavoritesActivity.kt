package com.example.effectivemobile

import VacancyAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.effectivemobile.data.Vacancy
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

class FavoritesActivity : AppCompatActivity() {

    private lateinit var title: TextView
    private lateinit var vacancyCount: TextView
    private lateinit var recyclerFavorites: RecyclerView

    private var vacancies: List<Vacancy> = emptyList()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        title = findViewById(R.id.title)
        vacancyCount = findViewById(R.id.number_of_favorites)
        recyclerFavorites = findViewById(R.id.recycler_favorites)


        // Инициализация данных
        initData()

        // Установка адаптера для RecyclerView
        setupRecyclerView()
    }

    private fun initData() {
        // Парсим JSON или инициализируем данные
        val inputStream = assets.open("mock_json.json")
        val json = InputStreamReader(inputStream).use { it.readText() }

        // Парсим JSON с помощью Gson
        val gson = Gson()
        val vacancyType = object : TypeToken<List<Vacancy>>(){}.type
        val jsonObject = gson.fromJson(json, JsonObject::class.java)
        vacancies = gson.fromJson(jsonObject.getAsJsonArray("favorites"), vacancyType)

        // Обновляем текст для количества вакансий
        updateVacancyCount()
    }

    private fun updateVacancyCount() {
        val count = vacancies.size
        val countText = when {
            count % 10 == 1 && count % 100 != 11 -> "вакансия"
            count % 10 in 2..4 && (count % 100 !in 12..14) -> "вакансии"
            else -> "вакансий"
        }
        vacancyCount.text = "$count $countText"
    }

    private fun setupRecyclerView() {
        recyclerFavorites.layoutManager = LinearLayoutManager(this)
        recyclerFavorites.adapter = VacancyAdapter(vacancies.toMutableList()) // Используйте VacancyAdapter
    }
}
