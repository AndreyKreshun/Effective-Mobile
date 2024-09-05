package com.example.effectivemobile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VerificationViewModel : ViewModel() {
    private val _code = MutableLiveData<String>()
    val code: LiveData<String> get() = _code

    private val _isCodeComplete = MutableLiveData<Boolean>()
    val isCodeComplete: LiveData<Boolean> get() = _isCodeComplete

    init {
        _code.value = ""
        _isCodeComplete.value = false
    }

    //Метод для обновления кода
    fun updateCode(index: Int, digit: String){
        val currentCode = _code.value ?: ""
        val updatedCode = StringBuilder(currentCode.padEnd(4)).apply {
            setCharAt(index, if (digit.isNotEmpty()) digit[0] else ' ')
        }.toString()

        _code.value = updatedCode

        // Проверяем, введены ли все цифры
        _isCodeComplete.value = updatedCode.length == 4 && updatedCode.all { it.isDigit() }
    }

    //Метод для очистки кода
    fun clearCode(){
        _code.value = ""
        _isCodeComplete.value = false
    }
}