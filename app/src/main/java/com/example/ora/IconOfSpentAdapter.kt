package com.example.ora

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ora.databinding.ItemIconBinding

class IconOfSpentAdapter(
    private val categories: List<CategoryUiData>,
    private val onCategorySelected: (CategoryUiData) -> Unit
) : RecyclerView.Adapter<IconOfSpentAdapter.CategoryViewHolder>() {

    private var selectedPosition = -1
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        val binding = ItemIconBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        position: Int
    ) {
        val category = categories[position]
        val isSelected = position == selectedPosition
        holder.bind(category, isSelected)
    }

    override fun getItemCount(): Int = categories.size


    inner class CategoryViewHolder(
        private val binding: ItemIconBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: CategoryUiData, isSelected: Boolean) {
            binding.ivIcon.setImageResource(category.icon)

            binding.ivIcon.background = ContextCompat.getDrawable(
                binding.root.context,
                if (isSelected) R.drawable.bg_selected else R.drawable.bg_unselected
            )
            binding.root.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(previousPosition)
                notifyItemChanged(previousPosition)
                onCategorySelected(category)
            }
        }
    }
}