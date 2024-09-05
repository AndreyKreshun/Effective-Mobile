package com.example.effectivemobile.data

import android.widget.Button

data class Offer(
    val id: String?,
    val title: String,
    val link: String,
    val button: Button? = null
)
