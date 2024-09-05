package com.example.effectivemobile

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    // LiveData для хранения текста email
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    // LiveData для хранения текста пароля (если нужно)
    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    // Метод для обновления текста email
    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
    }

    // Метод для обновления текста пароля (если нужно)
    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    // Метод для проверки валидности email (простой пример)
    fun isEmailValid(): Boolean {
        return !email.value.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email.value!!).matches()
    }

    // Метод для проверки валидности пароля (если нужно)
    fun isPasswordValid(): Boolean {
        return _password.value?.length ?: 0 >= 6
    }

    // Метод для управления логикой входа (например, проверка данных)
    fun login(): Boolean {
        return isEmailValid()
    }
}