package com.example.ora

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryDao: CategoryDao) : ViewModel() {

    val allCategories: LiveData<List<CategoryEntity>> =
        categoryDao.getAllCategories()

    fun insert( name: String, icon: Int) {
        viewModelScope.launch {
            val newCategory = CategoryEntity(name = name, icon = icon, isSelected = false)
           categoryDao.insertCategory(newCategory)
        }
    }

    fun delete(category: CategoryEntity){
        viewModelScope.launch {
            categoryDao.deleteCategory(category)
        }
    }
}