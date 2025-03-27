package com.example.ora

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ora.databinding.ItemSpentBinding

class SpentListAdapter  (
    private val expenses: List<SpentUiData>,
): RecyclerView.Adapter<SpentListAdapter.SpentViewHolder>(){

    inner class SpentViewHolder (private val binding: ItemSpentBinding):
    RecyclerView.ViewHolder(binding.root){

        fun bind(spent: SpentUiData){
            binding.ivSpentIcon.setImageResource(spent.icon)
            binding.tvCategoryName.text = spent.name
            binding.tvSpentValue.text = spent.amount.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpentViewHolder {
        val binding = ItemSpentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return expenses.size
    }

    override fun onBindViewHolder(holder: SpentViewHolder, position: Int) {
        holder.bind(expenses[position])
    }

}