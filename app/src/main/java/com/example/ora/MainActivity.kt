package com.example.ora

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ora.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

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

        spentViewModel.totalSpent.observe(this) { total ->
            binding.tvValueSpent.text = "R$ %.2f".format(total ?: 0.0)
        }

        binding.btnCreateEmptyView.setOnClickListener {
            showCreateCategoryBottomSheet(realCategories)
            setupCategoryList()
        }

        binding.btnAddSpent.setOnClickListener {
            showCreateOrUpdateSpentBottomSheet()
        }

    }

    private fun setupCategoryList() {

        categoryViewModel.allCategories.observe(this) { categories ->
            realCategories = categories.map {
                it.toUiData()
            }.filter { it.name != "+" && it.name != "all" }

            if (realCategories.isEmpty()){
                binding.llSpentTotal.isVisible = false
                binding.tvTitleRvCategory.isVisible = false
                binding.rvCategory.isVisible = false
                binding.ctnTitleFilterDownload.isVisible = false
                binding.llEmptyView.isVisible = true
                binding.llExpensesDetails.isVisible = false
                binding.btnAddSpent.isVisible = false
            } else {
                binding.llSpentTotal.isVisible = true
                binding.tvTitleRvCategory.isVisible = true
                binding.rvCategory.isVisible = true
                binding.ctnTitleFilterDownload.isVisible = true
                binding.llEmptyView.isVisible = false
                binding.llExpensesDetails.isVisible = true
                binding.btnAddSpent.isVisible = true
            }

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

