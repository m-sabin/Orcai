package com.example.ora

import android.R
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SpentViewModel(private val spentDao: SpentDao) : ViewModel() {
    val allSpents: LiveData<List<SpentEntity>> =
        spentDao.getAllSpents()

    fun insert(amount: Double, category: String, icon: Int) {
        viewModelScope.launch {
            val newSpent = SpentEntity(
                amount = amount,
                category = category,
                icon = icon,
                color = R.color.darker_gray
            )
            spentDao.insertSpent(newSpent)
        }
    }

    fun delete(spent: SpentEntity) {
        viewModelScope.launch {
            spentDao.deleteSpent(spent)
        }
    }

}


