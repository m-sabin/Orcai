package com.example.ora

import android.R
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SpentViewModel(private val spentDao: SpentDao) : ViewModel() {
    val allSpents: LiveData<List<SpentEntity>> =
        spentDao.getAllSpents()

    fun insert(spent: SpentEntity) {
        viewModelScope.launch {
            spentDao.insertSpent(spent)
        }
    }

    fun delete(spent: SpentEntity) {
        viewModelScope.launch {
            spentDao.deleteSpent(spent)
        }
    }

    fun update(spent: SpentEntity) {
        viewModelScope.launch {
            spentDao.updateSpent(spent)
        }
    }

    fun deleteByIcon(icon: Int){
        viewModelScope.launch {
            spentDao.deleteByIcon(icon)
        }
    }

}


