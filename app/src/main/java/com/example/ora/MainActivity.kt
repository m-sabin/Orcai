package com.example.ora

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var spentViewModel: SpentViewModel

    private val spentList = mutableListOf<SpentUiData>()
    private var realCategories: List<CategoryUiData> = emptyList()
    private lateinit var spentAdapter: SpentListAdapter

    private var selectedCategoryName: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = OrcaiDatabase.getDatabase(this)
        categoryViewModel = CategoryViewModel(db.getCategoryDao())
        spentViewModel = SpentViewModel(db.getSpentDao())

        setupCategoryList()
        setupSpentList()

        binding.btnAddSpent.setOnClickListener {
            showCreateSpentBottomSheet()
        }
    }

    private fun setupCategoryList() {
        categoryViewModel.allCategories.observe(this) { categories ->
            realCategories = categories.map {
                it.toUiData()
            }.filter { it.name != "+" && it.name != "all" }

            val allCategory = CategoryUiData(
                name = "all",
                icon = R.drawable.ic_filter,
                color = R.color.cyan,
                isSelected = true
            )
            val plusCategory = CategoryUiData(
                name = "+",
                icon = R.drawable.ic_add,
                color = R.color.primary400,
                isSelected = false
            )

            val categoryUiList = mutableListOf<CategoryUiData>().apply {
                add(allCategory)
                addAll(realCategories)
                add(plusCategory)
            }


            val adapter = CategoryListAdapter(
                categoryUiList,
                onCategorySelected = { category ->
                    when (category.name) {
                        "+" -> {}
                        "all" -> {
                            selectedCategoryName = null
                            setupSpentList()
                        }
                        else -> {
//                            selectedCategoryName = category.name
                            setupSpentList(category.icon)
                        }
                    }
                },
                onAddCategoryClick = {
                    showCreateCategoryBottomSheet()
                }
            )
            binding.rvCategory.adapter = adapter
        }

    }

    private fun showCreateCategoryBottomSheet() {
        val bottomSheet = CreateCategoryBottomSheet(
            onCategoryCreated = { name, icon, color ->
                val category = CategoryUiData(
                    name = name,
                    icon = icon,
                    color = color,
                    isSelected = false
                )
                categoryViewModel.insert(category)
            }
        )
        bottomSheet.show(supportFragmentManager, "NewCategoryBottomSheet")

    }

    private fun setupSpentList(filterIcon: Int? = null) {
        spentViewModel.allSpents.observe(this) { expenses ->
            val filteredList = if (filterIcon != null) {
                expenses.filter { it.icon == filterIcon }
            } else {
                expenses
            }
            val spentUiList = filteredList.map {
                it.toUiData()
            }
            spentAdapter = SpentListAdapter(spentUiList) { spent ->
                // ação do clique do gasto
            }
            binding.rvSpent.layoutManager = LinearLayoutManager(this)
            binding.rvSpent.adapter = spentAdapter
        }
    }

    private fun showCreateSpentBottomSheet() {
        val bottomSheet = CreateSpentBottomSheet(realCategories) { newspent ->
            spentViewModel.insert(newspent.toEntity())
        }
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }
}

