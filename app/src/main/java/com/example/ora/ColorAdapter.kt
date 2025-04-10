    package com.example.ora

    import android.view.LayoutInflater
    import android.view.ViewGroup
    import androidx.core.content.ContextCompat
    import androidx.recyclerview.widget.RecyclerView
    import com.example.ora.databinding.ItemColorBinding

    class ColorAdapter(
        private val colors: List<Int>,
        private val onColorSelected: (Int) -> Unit
    ) : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

        private var selectedPosition = -1

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ColorViewHolder {
            val binding = ItemColorBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return ColorViewHolder(binding)
        }

        override fun onBindViewHolder(
            holder: ColorViewHolder,
            position: Int
        ) {
            val colorRes = colors[position]
            val isSelected = position == selectedPosition
            holder.bind(colorRes, isSelected)
        }

        override fun getItemCount(): Int = colors.size

        inner class ColorViewHolder(
            private val binding: ItemColorBinding
        ) : RecyclerView.ViewHolder(binding.root){

            fun bind (colorRes: Int, isSelected: Boolean){

                binding.colorView.setBackgroundResource(R.drawable.bg_circle)

                binding.colorView.setBackgroundColor(colorRes)

                binding.borderView.setBackgroundResource(
                    if (isSelected) R.drawable.bg_selected_color else R.drawable.bg_unselected
                )


                binding.root.setOnClickListener {
                    val previous = selectedPosition
                    selectedPosition = adapterPosition
                    notifyItemChanged(previous)
                    notifyItemChanged(selectedPosition)
                    onColorSelected(colorRes)
                }
            }
        }
    }