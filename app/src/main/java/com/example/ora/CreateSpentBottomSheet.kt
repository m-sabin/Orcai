package com.example.ora

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ora.databinding.SpentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class CreateSpentBottomSheet(
    private val onSpentCreated: (SpentUiData) -> Unit
) : BottomSheetDialogFragment() {

    private var selectedCategory: CategoryUiData? = null

    private var _binding: SpentBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: IconOfSpentAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SpentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val dummyIcons = listOf(
            CategoryUiData("Casa", R.drawable.ic_home, R.color.cyan, false),
            CategoryUiData("Transporte", R.drawable.ic_car, R.color.lime, false),
            CategoryUiData("Mercado", R.drawable.ic_shopping, R.color.lime, false)
        )

        adapter = IconOfSpentAdapter(dummyIcons) { category ->
            selectedCategory = category
        }

        binding.rvSpentIcon.adapter = adapter
        binding.rvSpentIcon.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.btnCreateSpent.setOnClickListener {
            val nameSpent = binding.etSpentName.text.toString().trim()
            val amountSpent = binding.etSpentValue.text.toString().toDoubleOrNull()

            if (nameSpent.isNotBlank() && amountSpent != null && selectedCategory != null) {
                val spent = SpentUiData(nameSpent, amountSpent, selectedCategory!!.icon)
                onSpentCreated(spent)
                dismiss()
            } else {
                Snackbar.make(binding.root, "Preenche Todos Os Campos", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

