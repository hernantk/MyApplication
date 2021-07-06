package br.hernan.myapplication.ui.memory

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.hernan.myapplication.R
import br.hernan.myapplication.databinding.ItemMemoryBinding
import br.hernan.myapplication.domain.dto.MemoryDto
import java.time.format.DateTimeFormatter

class MemoryAdapter:RecyclerView.Adapter<MemoryAdapter.MemoryViewHolder>() {

    var memory:List<MemoryDto> = listOf()
    set(value){
        field = value
        notifyDataSetChanged()
    }


    inner class MemoryViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private val binding = ItemMemoryBinding.bind(itemView)

        fun bind(memory:MemoryDto){
            val dateFormater = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            binding.tvDate.text = memory.date.format(dateFormater).toString()
            binding.tvCity.text = memory.city
            binding.tvDescription.text = memory.description

            val image = Base64.decode(memory.image,Base64.DEFAULT)
            binding.imgCamera.setImageBitmap(BitmapFactory.decodeByteArray(image,0, image.size))



    }
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoryViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.item_memory,parent,false)
        return MemoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MemoryViewHolder, position: Int) {
        holder.bind(memory[position])
    }

    override fun getItemCount(): Int {
        return memory.size
    }
}
