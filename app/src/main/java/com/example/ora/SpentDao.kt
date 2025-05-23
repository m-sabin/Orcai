package com.example.ora

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface SpentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpent(spent: SpentEntity)

    @Query("SELECT * FROM spent_table ORDER BY id DESC")
    fun getAllSpents(): LiveData<List<SpentEntity>>

    @Delete
    suspend fun deleteSpent(spent: SpentEntity)

    @Update
    suspend fun updateSpent(spent: SpentEntity)

    @Query("DELETE FROM spent_table WHERE icon = :icon")
    suspend fun deleteByIcon(icon: Int)

    @Query("SELECT SUM(amount) FROM spent_table")
    fun getTotalSpent(): LiveData<Double>

}
