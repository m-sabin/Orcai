package com.example.ora

import android.graphics.Color
import androidx.annotation.DrawableRes

data class SpentUiData(
    val category: String,
    val amount: Double,
    @DrawableRes val icon: Int,
)
