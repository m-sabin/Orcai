package com.example.ora

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ora.databinding.ItemCategoryBinding

class  CategoryListAdapter(
    private var categories: List<CategoryUiData>,
    private val onCategorySelected: (CategoryUiData) -> Unit,
    private val onAddCategoryClick: () -> Unit
) : RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>(){

    inner class CategoryViewHolder (private val binding: ItemCategoryBinding):
    RecyclerView.ViewHolder(binding.root){

        fun bind (category: CategoryUiData){
            binding.root.isSelected = category.isSelected
            binding.ivCategoryIcon.setImageResource(category.icon)

            binding.root.setOnClickListener {
                if (category.name == "+"){
                    onAddCategoryClick()
                } else {
                    onCategorySelected(category)
                }
            }
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

    fun updateList(newList: List<CategoryUiData>){
        categories = newList
        notifyDataSetChanged()
    }
}