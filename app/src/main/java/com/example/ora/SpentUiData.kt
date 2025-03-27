package com.example.ora

import androidx.annotation.DrawableRes

data class SpentUiData(
    val amount: Double,
    val name: String,
    @DrawableRes val icon: Int
)
