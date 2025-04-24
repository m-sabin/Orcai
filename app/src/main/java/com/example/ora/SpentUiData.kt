package com.example.ora

import android.graphics.Color
import androidx.annotation.DrawableRes

data class SpentUiData(
    val id: Int,
    val category: String,
    val amount: Double,
    @DrawableRes val icon: Int,
    val color: Int
)
