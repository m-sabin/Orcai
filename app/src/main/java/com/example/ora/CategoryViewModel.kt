package com.example.ora

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryDao: CategoryDao) : ViewModel() {

    val allCategories: LiveData<List<CategoryEntity>> =
        categoryDao.getAllCategories()

    fun insert( category: CategoryUiData) {
        viewModelScope.launch {
            categoryDao.insertCategory(category.toEntity())

        }
    }

    fun delete(category: CategoryEntity){
        viewModelScope.launch {
            categoryDao.deleteCategory(category)
        }
    }
}