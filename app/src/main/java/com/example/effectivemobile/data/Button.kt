package com.example.effectivemobile.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Button(
    val text: String
) : Parcelable
