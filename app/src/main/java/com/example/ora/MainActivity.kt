package com.example.ora

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryAdapter = CategoryListAdapter(categories, onCategoryClick = {})
        binding.rvCategory.adapter = categoryAdapter

        val spentAdapter = SpentListAdapter(expenses)
        binding.rvSpent.adapter = spentAdapter

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