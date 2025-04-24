package com.example.ora

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var spentViewModel: SpentViewModel

    private var realCategories: List<CategoryUiData> = emptyList()
    private lateinit var spentAdapter: SpentListAdapter

    private lateinit var categoryAdapter: CategoryListAdapter

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
            showCreateOrUpdateSpentBottomSheet()
        }
    }

    private fun setupCategoryList() {
        categoryViewModel.allCategories.observe(this) { categories ->
            realCategories = categories.map {
                it.toUiData()
            }.filter { it.name != "+" && it.name != "all" }

            val allCategory = CategoryUiData(
                id = 0,
                name = "all",
                icon = R.drawable.ic_filter,
                color = R.color.cyan,
                isSelected = true
            )
            val plusCategory = CategoryUiData(
                id = 0,
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

            categoryAdapter = CategoryListAdapter(
                categoryUiList,
                onCategorySelected = { selectedcategory ->
                    val updateList =
                        categoryUiList.map { it.copy(isSelected = it.name == selectedcategory.name) }

                    categoryAdapter.updateList(updateList)
                    when (selectedcategory.name) {
                        "+" -> {}
                        "all" -> {
                            selectedCategoryName = null
                            setupSpentList()
                        }

                        else -> {
//                            selectedCategoryName = category.name
                            setupSpentList(selectedcategory.icon)
                        }
                    }
                },
                onAddCategoryClick = {
                    showCreateCategoryBottomSheet(realCategories)
                },
                onCategoryLongClick = { category ->
                    AlertDialog.Builder(this)
                        .setTitle("Deletar Categoria")
                        .setMessage("Tem certeza que deseja deletar a categoria \"${category.name}\"? Todas as despesas associadas a ela também serão apagadas.")
                        .setPositiveButton("SIM") { _, _ ->
                            deleteCategoryAndSpents(category)
                        }
                        .setNegativeButton("CANCELAR", null)
                        .show()
                }
            )
            binding.rvCategory.adapter = categoryAdapter
        }

    }

    private fun showCreateCategoryBottomSheet(existingSpent: List<CategoryUiData>) {
        val bottomSheet = CreateCategoryBottomSheet(
            existingCategories = existingSpent,
            onCategoryCreated = { name, icon, color ->
                val newcategory = CategoryUiData(
                    id = 0,
                    name = name,
                    icon = icon,
                    color = color,
                    isSelected = false
                )
                categoryViewModel.insert(newcategory)
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
            spentAdapter = SpentListAdapter(
                spentUiList,
                onSpentClick = { spent ->
                    showCreateOrUpdateSpentBottomSheet(spent)
                }
            )
            binding.rvSpent.layoutManager = LinearLayoutManager(this)
            binding.rvSpent.adapter = spentAdapter
        }
    }

    private fun showCreateOrUpdateSpentBottomSheet(existingSpent: SpentUiData? = null) {
        val bottomSheet = CreateSpentBottomSheet(
            categories = realCategories,
            existingSpent = existingSpent,
            onSpentCreated = { newspent ->
                if (existingSpent == null) {
                    spentViewModel.insert(newspent.toEntity())

                } else {
                    spentViewModel.update(newspent.copy(id = existingSpent.id).toEntity())
                }
            },
            onSpentDelete = {
                existingSpent?.let { spentViewModel.delete(it.toEntity()) }
            }
        )
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }


    private fun deleteCategoryAndSpents(category: CategoryUiData) {
        spentViewModel.deleteByIcon(category.icon)

        categoryViewModel.delete(category.toEntity())

    }
}

