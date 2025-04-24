package com.example.ora

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ora.databinding.CategoryBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class CreateCategoryBottomSheet(
    private val existingCategories: List<CategoryUiData> = emptyList(),
    private val onCategoryCreated: (String, Int, Int) -> Unit

) : BottomSheetDialogFragment() {

    private var selectedIcon: Int? = null
    private var selectedColor: Int? = null

    private lateinit var allIcons: List<Int>
    private lateinit var allColors: List<Int>

    private lateinit var binding: CategoryBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CategoryBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        allIcons = listOf(
            R.drawable.ic_home,
            R.drawable.ic_key,
            R.drawable.ic_food,
            R.drawable.ic_shopping,
            R.drawable.ic_car,
            R.drawable.ic_travel,
            R.drawable.ic_beauty,
            R.drawable.ic_cloths,
            R.drawable.ic_streaming
        )

        val usedIcons = existingCategories.map { it.icon }
        val availableIcons = allIcons.filterNot { it in usedIcons }
        setupIconRecyclerView(availableIcons)

        allColors = listOf(
            requireContext().getColor(R.color.category_red),
            requireContext().getColor(R.color.category_blue),
            requireContext().getColor(R.color.category_green),
            requireContext().getColor(R.color.category_orange),
            requireContext().getColor(R.color.category_purple),
            requireContext().getColor(R.color.category_yellow),
            requireContext().getColor(R.color.teal_200),
            requireContext().getColor(R.color.teal_700),
            requireContext().getColor(R.color.pink_200),
            requireContext().getColor(R.color.pink_500),
            requireContext().getColor(R.color.indigo),
            requireContext().getColor(R.color.cyan),
            requireContext().getColor(R.color.deep_orange),
            requireContext().getColor(R.color.lime),
            requireContext().getColor(R.color.amber),
            requireContext().getColor(R.color.grey_700)
        )

        val usedColors = existingCategories.map { it.color }
        val availableColors = allColors.filterNot { it in usedColors }
        setupColorRecyclerView(availableColors)

        binding.btnCreatNewCategory.setOnClickListener {
            val name = binding.etCategoryName.text.toString().trim()
            if (name.isEmpty() || selectedIcon == null || selectedColor == null) {
                Snackbar.make(binding.root, "Preenche todos os campos", Snackbar.LENGTH_SHORT).show()
            } else {
                onCategoryCreated(name, selectedIcon!!, selectedColor!!)
                dismiss()
            }
        }
    }

    private fun setupIconRecyclerView(icons: List<Int>) {
        val adapter = IconAdapter(icons) { icon ->
            selectedIcon = icon
        }
        binding.rvIcons.adapter = adapter
        binding.rvIcons.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupColorRecyclerView(colors: List<Int>) {
        val adapter = ColorAdapter(colors) { color ->
            selectedColor = color
        }
        binding.rvColors.adapter = adapter
        binding.rvColors.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

}

