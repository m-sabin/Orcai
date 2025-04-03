package com.example.ora

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.ora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var spentViewModel: SpentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryAdapter = CategoryListAdapter(categories, onCategoryClick = {})
//        binding.rvCategory.adapter = categoryAdapter

        val spentAdapter = SpentListAdapter(expenses)
    //        binding.rvSpent.adapter = spentAdapter

        val db = OrcaiDatabase.getDatabase(this)
        val categoryDao = db.getCategoryDao()
        val spentDao = db.getSpentDao()

        categoryViewModel = ViewModelProvider(this, ViewModelFactory(categoryDao)
        )[CategoryViewModel::class.java]

        spentViewModel = ViewModelProvider(this, ViewModelFactory(spentDao)
        )[SpentViewModel::class.java]

        categoryViewModel.allCategories.observe(this){ categories ->
            for (category in categories){
                Log.d("RoomTest", "Categoria: ${category.name}, Selecionado: ${category.isSelected}")
            }
        }
        categoryViewModel.insert(CategoryEntity("Self", true))

        spentViewModel.allSpents.observe(this){ spents ->
            for (spent in spents)
                Log.d("RoomTest", "Despesa: ${spent.category}, Valor: ${spent.amount}")
        }
        spentViewModel.insert(SpentEntity(category = "Self", amount = 50.0, icon = R.drawable.ic_beauty))

    }
}

val categories = listOf(
    CategoryUiData(
        name = "Casa",
        R.drawable.ic_home,
        isSelected = false
    ),
    CategoryUiData(
        name = "Beleza",
        R.drawable.ic_beauty,
        isSelected = false
    ),
    CategoryUiData(
        name = "Streaming",
        R.drawable.ic_streaming,
        isSelected = false
    )
)

val expenses = listOf(
    SpentUiData(
        amount = 90.87,
        name = "Home",
        R.drawable.ic_home
    ),
    SpentUiData(
        amount = 100.89,
        name = "Beleza",
        R.drawable.ic_beauty
    ),
    SpentUiData(
        amount = 98.66,
        name = "Streaming",
        R.drawable.ic_streaming
    )
)