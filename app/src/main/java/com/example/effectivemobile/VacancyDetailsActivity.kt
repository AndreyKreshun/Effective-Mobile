package com.example.effectivemobile

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.effectivemobile.data.Vacancy
import com.google.gson.Gson

class VacancyDetailsActivity : AppCompatActivity() {

    private lateinit var vacancyTitle: TextView
    private lateinit var companyName: TextView
    private lateinit var salary: TextView
    private lateinit var experience: TextView
    private lateinit var employmentType: TextView
    private lateinit var appliedNumber: TextView
    private lateinit var lookingNumber: TextView
    private lateinit var companyAddress: TextView
    private lateinit var companyDescription: TextView
    private lateinit var responsibilities: TextView
    private lateinit var questionsContainer: LinearLayout
    private lateinit var applyButton: Button
    private lateinit var iconFavorite: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vacancy_details)

        // Инициализация UI элементов
        vacancyTitle = findViewById(R.id.vacancy_title)
        companyName = findViewById(R.id.company_name)
        salary = findViewById(R.id.salary)
        experience = findViewById(R.id.experience)
        employmentType = findViewById(R.id.employment_type)
        appliedNumber = findViewById(R.id.applied_number)
        lookingNumber = findViewById(R.id.looking_number)
        companyAddress = findViewById(R.id.company_address)
        companyDescription = findViewById(R.id.company_description)
        responsibilities = findViewById(R.id.responsibilities)
        questionsContainer = findViewById(R.id.questions_container)
        applyButton = findViewById(R.id.apply_button)
        iconFavorite = findViewById(R.id.icon_favorite)

        // Получение JSON-данных из Intent
        val vacancyJson = intent.getStringExtra("vacancy_json")
        val vacancy = vacancyJson?.let { parseVacancyJson(it) }

        // Заполнение UI данными
        if (vacancy != null) {
            displayVacancyData(vacancy)
        }
    }

    // Парсинг JSON строки в объект Vacancy
    private fun parseVacancyJson(jsonString: String): Vacancy {
        val gson = Gson()
        return gson.fromJson(jsonString, Vacancy::class.java)
    }

    // Заполнение UI компонент данными из объекта Vacancy
    private fun displayVacancyData(vacancy: Vacancy) {
        vacancyTitle.text = vacancy.title
        companyName.text = vacancy.company
        salary.text = vacancy.salary?.full ?: "Зарплата не указана"
        experience.text = "Требуемый опыт: ${vacancy.experience.text}"
        employmentType.text = vacancy.schedules.joinToString(", ") { it.capitalize() }
        appliedNumber.text = "${vacancy.appliedNumber ?: 0} человек уже откликнулось"
        lookingNumber.text = "${vacancy.lookingNumber ?: 0} человек сейчас смотрят"
        companyAddress.text = "${vacancy.address.town}, ${vacancy.address.street}, ${vacancy.address.house}"

        companyDescription.text = vacancy.description?.replace("\\n", "\n") ?: "Описание отсутствует"
        responsibilities.text = vacancy.responsibilities?.replace("\\n", "\n") ?: "Обязанности отсутствуют"

        // Отображение вопросов
        questionsContainer.removeAllViews()
        vacancy.questions.forEach { question ->
            val questionTextView = TextView(this)
            questionTextView.text = question
            questionTextView.setOnClickListener {
                showQuestionModal(question)
            }
            questionsContainer.addView(questionTextView)
        }

        // Обработка клика на иконку "Избранное"
        /*iconFavorite.setImageResource(
            if (vacancy.isFavorite) R.drawable.ic_favorite_filled
            else R.drawable.ic_favorite_border
        )*/

        iconFavorite.setOnClickListener {
            vacancy.isFavorite = !vacancy.isFavorite
            /*iconFavorite.setImageResource(
                if (vacancy.isFavorite) R.drawable.ic_favorite_filled
                else R.drawable.ic_favorite_border
            )*/
            saveToFavorites(vacancy)
        }

        // Обработка клика на кнопку "Откликнуться"
        applyButton.setOnClickListener {
            showApplyModal()
        }
    }

    private fun saveToFavorites(vacancy: Vacancy) {
        // Логика сохранения вакансии в избранное
    }

    private fun showQuestionModal(question: String) {
        // Логика отображения модального окна с вопросом
    }

    private fun showApplyModal() {
        // Логика отображения модального окна для отклика на вакансию
    }
}
