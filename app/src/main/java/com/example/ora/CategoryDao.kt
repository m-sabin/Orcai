package com.example.ora

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: CategoryEntity)

    @Query("SELECT * FROM category_spent")
    fun getAllCategories(): LiveData<List<CategoryEntity>>

    @Delete
   suspend fun deleteCategory(category: CategoryEntity)

}
