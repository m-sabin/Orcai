package com.example.ora

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Delete
import com.example.ora.databinding.SpentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class CreateSpentBottomSheet(
    private val categories: List<CategoryUiData>,
    private val existingSpent: SpentUiData? = null,
    private val onSpentCreated: (SpentUiData) -> Unit,
    private val onSpentDelete: ((SpentUiData) -> Unit)? = null
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

        existingSpent?.let { spent ->
            binding.etSpentName.setText(spent.category)
            binding.etSpentValue.setText(spent.amount.toString())
            selectedCategory = categories.find { it.icon == spent.icon }
        }

        adapter = IconOfSpentAdapter(categories) { category ->
            selectedCategory = category
        }

        binding.rvSpentIcon.adapter = adapter
        binding.rvSpentIcon.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Atualizar o botao (Criar ou Atualizar)
        binding.btnCreateSpent.text = if (existingSpent != null) "Atualizar" else "Criar"

        binding.btnCreateSpent.setOnClickListener {
            val nameSpent = binding.etSpentName.text.toString().trim()
            val amountSpent = binding.etSpentValue.text.toString().toDoubleOrNull()

            if (nameSpent.isNotBlank() && amountSpent != null && selectedCategory != null) {
                val spent = SpentUiData(
                    id = existingSpent?.id ?: 0,
                    category = nameSpent,
                    amount = amountSpent,
                    icon = selectedCategory!!.icon,
                    color = selectedCategory!!.color
                )
                onSpentCreated(spent)
                dismiss()
            } else {
                Snackbar.make(binding.root, "Preenche Todos Os Campos", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

        if (existingSpent != null) {
            binding.btnDeleteSpent.visibility = View.VISIBLE
            binding.btnDeleteSpent.setOnClickListener {
                AlertDialog.Builder(requireContext())
                    .setTitle("Deletar despesa")
                    .setMessage("Tem certeza que deseja deletar essa despesa?")
                    .setPositiveButton("SIM") { _, _ ->
                        onSpentDelete?.invoke(existingSpent)
                        dismiss()
                    }
                    .setNegativeButton("CANCELAR", null)
                    .show()
            }
        } else {
            binding.btnDeleteSpent.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

