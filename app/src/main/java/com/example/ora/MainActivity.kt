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
            private lateinit var spentAdapter: SpentListAdapter


            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                binding = ActivityMainBinding.inflate(layoutInflater)
                setContentView(binding.root)

                val db = OrcaiDatabase.getDatabase(this)
                categoryViewModel = CategoryViewModel(db.getCategoryDao())
                spentViewModel = SpentViewModel(db.getSpentDao())

                setupCategoryList()

                spentAdapter = SpentListAdapter(spentList){}
                binding.rvSpent.layoutManager = LinearLayoutManager(this)
                binding.rvSpent.adapter = spentAdapter

                binding.btnAddSpent.setOnClickListener {
                    val bottomSheet = CreateSpentBottomSheet{newspent ->
                        spentList.add(newspent)
                        spentAdapter.notifyItemInserted(spentList.size -1)
                    }
                    bottomSheet.show(supportFragmentManager, bottomSheet.tag)
                    setupCategoryList()
                }
            }

            private fun setupCategoryList() {
                categoryViewModel.allCategories.observe(this) { categories ->
                    val categoryUiList = categories.map {
                        it.toUiData()
                    }.toMutableList()

                    val plusCategory = CategoryUiData(
                        name = "+",
                        icon = R.drawable.ic_add,
                        color = R.color.primary400,
                        isSelected = false
                    )

                    if (categoryUiList.none { it.name == "+" }) {
                        categoryUiList.add(plusCategory)
                    }

                    val adapter = CategoryListAdapter(
                        categoryUiList,
                        onCategorySelected = { category ->

                        },
                        onAddCategoryClick = {
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
                    )
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
        }

