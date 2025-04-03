package com.example.ora

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SpentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertSpent(spent: SpentEntity)

    @Query("SELECT * FROM spent_table ORDER BY id DESC")
    fun getAllSpents(): LiveData<List<SpentEntity>>

    @Delete
   suspend fun deleteSpent(spent: SpentEntity)

}
