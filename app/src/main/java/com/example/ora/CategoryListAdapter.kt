package com.example.ora

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ora.databinding.ItemCategoryBinding

class  CategoryListAdapter(
    private var categories: List<CategoryUiData>,
    private val onCategorySelected: (CategoryUiData) -> Unit,
    private val onAddCategoryClick: () -> Unit,
    private val onCategoryLongClick: (CategoryUiData) -> Unit
) : RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>(){

    inner class CategoryViewHolder (private val binding: ItemCategoryBinding):
    RecyclerView.ViewHolder(binding.root){

        fun bind (category: CategoryUiData){
            with(binding) {
                categoryItemRoot.isSelected = category.isSelected
                ivCategoryIcon.setImageResource(category.icon)

                categoryItemRoot.setOnClickListener {
                    if (category.name == "+"){
                        onAddCategoryClick()
                    } else {
                        onCategorySelected(category)
                    }
                }

                categoryItemRoot.setOnLongClickListener {
                    if (category.name != "+" && category.name != "all"){
                        onCategoryLongClick(category)
                    }
                    true
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