package com.example.ora

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var spentViewModel: SpentViewModel

    private lateinit var categories: CategoryUiData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = OrcaiDatabase.getDatabase(this)
        categoryViewModel = CategoryViewModel(db.getCategoryDao())
        spentViewModel = SpentViewModel(db.getSpentDao())

        setupCategoryList(binding)
        setupSpentList()
        insertInitialCategoriesIfEmpty()

//        getCategoryFromDb()
//        getSpentFromDb()

//        val categoryAdapter = CategoryListAdapter(categories, onCategoryClick = {})
//        binding.rvCategory.adapter = categoryAdapter

//        val spentAdapter = SpentListAdapter(expenses, onSpentClick = {})
//        binding.rvSpent.adapter = spentAdapte r


    }

    private fun insertInitialCategoriesIfEmpty() {
        categoryViewModel.allCategories.observe(this) { categories ->
            if (categories.isEmpty()) {
                val initialCategories = listOf(
                    CategoryUiData("add", R.drawable.ic_add, false)
                )

                initialCategories.forEach{ categoryUiList ->
                    categoryViewModel.insert(categoryUiList.name, categoryUiList.icon)
                }
            }
        }
    }

    private fun setupCategoryList(binding: ActivityMainBinding) {
        categoryViewModel.allCategories.observe(this) { categories ->
            val categoryUiList = categories.map {
                it.toUiData()
            }
            val adapter = CategoryListAdapter(categoryUiList) { category ->
            }
            binding.rvCategory.adapter = adapter
        }

    }

    private fun setupSpentList() {
        spentViewModel.allSpents.observe(this) { expenses ->
            val spentUiList = expenses.map {
                it.toUiData()
            }
            val adapter = SpentListAdapter(spentUiList) { spent ->
            }
            binding.rvSpent.adapter = adapter
        }
    }

//    private fun getCategoryFromDb() {
//        categories.forEach { category ->
//            categoryViewModel.insert(category.name, category.icon)
//        }
//    }

//    private fun getSpentFromDb() {
//        expenses.forEach { spent ->
//            spentViewModel.insert(spent.amount, spent.category, spent.icon)
//        }
//    }
}

