package com.example.ora

fun CategoryUiData.toEntity(): CategoryEntity{
    return CategoryEntity(
        name = this.name,
        icon = this.icon,
        color = this.color,
        isSelected = this.isSelected
    )
}

fun CategoryEntity.toUiData(): CategoryUiData{
    return CategoryUiData(
        name = this.name,
        icon = this.icon,
        color = this.color,
        isSelected = this.isSelected
    )
}

fun SpentUiData.toEntity(): SpentEntity{
    return SpentEntity(
        amount = this.amount,
        category = this.category,
        icon = this.icon,
        color = this.color
    )
}

fun SpentEntity.toUiData(): SpentUiData{
    return SpentUiData(
        amount = this.amount,
        category = this.category,
        icon = this.icon,
        color = this.color
    )
}