package com.example.ora

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_spent")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "key")
    val name: String,

    @ColumnInfo(name = "icon")
    val icon: Int,

    @ColumnInfo(name = "is_selected")
    val isSelected: Boolean
)

