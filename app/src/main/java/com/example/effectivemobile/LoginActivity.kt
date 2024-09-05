package com.example.effectivemobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var btnLogin: Button

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.et_personal_login)
        btnLogin = findViewById(R.id.btn_personal_login)

        //Наблюдаем за изменениями в email и обновляем UI
        loginViewModel.email.observe(this, Observer { email ->
            etEmail.setText(email)
            btnLogin.isEnabled = loginViewModel.isEmailValid()
            btnLogin.alpha = if (loginViewModel.isEmailValid()) 1.0f else 0.5f
        })

        //Обработка изменения текста email
        etEmail.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?){

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //loginViewModel.onPasswordChanged(s.toString())
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()) {
                    btnLogin.isEnabled = true
                    //btnLogin.setBackgroundColor(ContextCompat.getColor(this, R.color.active_button_color))
                }
                else{
                    btnLogin.isEnabled = false
                    etEmail.setError("Вы ввели неверный email")
                }
                //Показ иконки крестика, если есть текст
                val isEmailEmpty = s.isNullOrEmpty()
                etEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, if (isEmailEmpty)0 else R.drawable.ic_clear, 0)
            }

        })

        //Обработка клика по иконке крестика
        etEmail.setOnTouchListener{v, event ->
            if(event.action == android.view.MotionEvent.ACTION_UP){
                if (event.rawX >= (etEmail.right - etEmail.compoundPaddingEnd)){
                    etEmail.text.clear()
                    return@setOnTouchListener true
                }
            }
            false
        }

        btnLogin.setOnClickListener {
                val intent = Intent(this, VerificationActivity::class.java)
                intent.putExtra("email", etEmail.text.toString())
                startActivity(intent)
        }
    }

}
