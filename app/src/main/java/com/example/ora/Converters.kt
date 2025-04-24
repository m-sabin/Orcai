package com.example.ora

fun CategoryUiData.toEntity(): CategoryEntity{
    return CategoryEntity(
        id = this.id,
        name = this.name,
        icon = this.icon,
        color = this.color,
        isSelected = this.isSelected
    )
}

fun CategoryEntity.toUiData(): CategoryUiData{
    return CategoryUiData(
        id = this.id,
        name = this.name,
        icon = this.icon,
        color = this.color,
        isSelected = this.isSelected
    )
}

fun SpentUiData.toEntity(): SpentEntity{
    return SpentEntity(
        id = this.id,
        amount = this.amount,
        category = this.category,
        icon = this.icon,
        color = this.color
        )
}

fun SpentEntity.toUiData(): SpentUiData{
    return SpentUiData(
        id = this.id,
        amount = this.amount,
        category = this.category,
        icon = this.icon,
        color = this.color
    )
}