package com.example.effectivemobile

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class VerificationActivity : AppCompatActivity() {

    private lateinit var etCode1: EditText
    private lateinit var etCode2: EditText
    private lateinit var etCode3: EditText
    private lateinit var etCode4: EditText
    private lateinit var btnConfirm: Button

    private val viewModel: VerificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

        etCode1 = findViewById(R.id.et_code_1)
        etCode2 = findViewById(R.id.et_code_2)
        etCode3 = findViewById(R.id.et_code_3)
        etCode4 = findViewById(R.id.et_code_4)
        btnConfirm = findViewById(R.id.btn_confirm)

        // Установка текста с email, введенным пользователем ранее
        val userEmail = intent.getStringExtra("email")
        val tvVerificationMessage: TextView = findViewById(R.id.tv_verification_message)
        tvVerificationMessage.text = "Отправили код на $userEmail"

        // Связываем EditText с ViewModel
        setupCodeEditTexts()

        // Наблюдаем за изменением состояния кода
        viewModel.isCodeComplete.observe(this, Observer { isComplete ->
            btnConfirm.isEnabled = isComplete
        })

        // Активация кнопки только при вводе 4 цифр
        btnConfirm.setOnClickListener {
            if (viewModel.isCodeComplete.value == true) {
                // Логика для перехода на главный экран
                startActivity(Intent(this, MainActivity::class.java))
                // finish()
            }
        }
    }

    private fun setupCodeEditTexts() {
        val editTexts = arrayOf(etCode1, etCode2, etCode3, etCode4)

        for (i in editTexts.indices) {
            val currentEditText = editTexts[i]
            val nextEditText = if (i < editTexts.size - 1) editTexts[i + 1] else null

            currentEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1) {
                        nextEditText?.requestFocus()
                        viewModel.updateCode(i, s.toString())
                    } else {
                        viewModel.updateCode(i, "")
                    }

                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    /*private fun checkCodeCompletion() {
        val isCodeComplete = isCodeEntered()
        btnConfirm.isEnabled = isCodeComplete
    }*/

    /*private fun isCodeEntered(): Boolean {
        return etCode1.text.isNotEmpty() &&
                etCode2.text.isNotEmpty() &&
                etCode3.text.isNotEmpty() &&
                etCode4.text.isNotEmpty()
    }*/
}
