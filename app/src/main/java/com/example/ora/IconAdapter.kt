package com.example.ora

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ora.databinding.ItemIconBinding

class IconAdapter(
    private val icons: List<Int>,
    private val onIconSelected: (Int) -> Unit
) : RecyclerView.Adapter<IconAdapter.IconViewHolder>() {

    private var selectedPosition = -1


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IconViewHolder {
        val binding = ItemIconBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return IconViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: IconViewHolder,
        position: Int
    ) {
        val iconRes = icons[position]
        val isSelected = position == selectedPosition
        holder.bind(iconRes, isSelected)
    }

    override fun getItemCount(): Int = icons.size

    inner class IconViewHolder(
        private val binding: ItemIconBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(iconRes: Int, isSelected: Boolean) {
            binding.ivIcon.setImageResource(iconRes)

            binding.ivIcon.background = ContextCompat.getDrawable(
                binding.root.context,
                if (isSelected) R.drawable.bg_selected else R.drawable.bg_unselected
            )

            binding.root.setOnClickListener {
                val previousPosition =  selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)
                onIconSelected(iconRes)
            }
        }
    }


}