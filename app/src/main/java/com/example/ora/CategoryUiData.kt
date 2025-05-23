package com.example.ora

import androidx.annotation.DrawableRes

data class CategoryUiData(
    val id: Int,
    val name: String,
    @DrawableRes val icon: Int,
    val color: Int,
    val isSelected: Boolean

)
