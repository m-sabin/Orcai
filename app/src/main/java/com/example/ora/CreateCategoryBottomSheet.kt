package com.example.ora

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ora.databinding.CategoryBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class NewCategoryBottomSheet(
    private val  onCategoryCreated: (String, Int, Int) -> Unit

): BottomSheetDialogFragment(){

    private var selectedIcon: Int? = null

    private var selectedColor: Int? = null

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
        setupIconRecyclerView()
        setupColorRecyclerView()

        binding.btnCreatNewCategory.setOnClickListener {
            val name = binding.etCategoryName.text.toString().trim()

            if (name.isEmpty() || selectedIcon == null || selectedColor == null){
                Snackbar.make(binding.root, "Preenche Todos Os Campos", Snackbar.LENGTH_SHORT).show()
            } else {
                onCategoryCreated(name, selectedIcon!!, selectedColor!!)
                dismiss()
            }
        }
    }

    private fun setupIconRecyclerView(){
        val iconList = listOf(
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

        val adapter = IconAdapter(iconList){
            selectedIcon = it
        }
        binding.rvIcons.adapter = adapter
        binding.rvIcons.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupColorRecyclerView(){
        val context = requireContext()
        val colorList = listOf(
            context.getColor(R.color.category_red),
            context.getColor(R.color.category_blue),
            context.getColor(R.color.category_green),
            context.getColor(R.color.category_orange),
            context.getColor(R.color.category_purple),
            context.getColor(R.color.category_yellow),
            context.getColor(R.color.teal_200),
            context.getColor(R.color.teal_700),
            context.getColor(R.color.pink_200),
            context.getColor(R.color.pink_500),
            context.getColor(R.color.indigo),
            context.getColor(R.color.cyan),
            context.getColor(R.color.deep_orange),
            context.getColor(R.color.lime),
            context.getColor(R.color.amber),
            context.getColor(R.color.grey_700)
        )

        val adapter = ColorAdapter(colorList){
            selectedColor = it
        }
        binding.rvColors.adapter = adapter
        binding.rvColors.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

}

