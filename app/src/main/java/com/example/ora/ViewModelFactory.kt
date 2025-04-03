package com.example.ora

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val dao: Any) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CategoryViewModel::class.java) -> CategoryViewModel(dao as CategoryDao) as T
            modelClass.isAssignableFrom(SpentViewModel::class.java) -> SpentViewModel(dao as SpentDao) as T
            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}