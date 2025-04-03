package com.example.ora

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "spent_table")
data class SpentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name= "amount")
    val amount: Double,

    @ColumnInfo(name = "icon")
    val icon: Int
)
