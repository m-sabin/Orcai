package com.example.ora

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ora.databinding.ItemCategoryBinding

class  CategoryListAdapter (
    private val categories: List<CategoryUiData>,
    private val onCategoryClick: (CategoryUiData) -> Unit
) : RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>(){

    inner class CategoryViewHolder (private val binding: ItemCategoryBinding):
    RecyclerView.ViewHolder(binding.root){

        fun bind (category: CategoryUiData){
            binding.ivCategorieIcon.setImageResource(category.icon)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

}