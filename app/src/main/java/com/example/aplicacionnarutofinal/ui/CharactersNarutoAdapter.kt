package com.example.aplicacionnarutofinal.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionnarutofinal.R
import com.example.aplicacionnarutofinal.model.CharacterNaruto

class CharactersNarutoAdapter(private val onItemClick: (CharacterNaruto) -> Unit) : RecyclerView.Adapter<CharacterViewHolder>() {

    private var items: MutableList<CharacterNaruto> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_characternaruto, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = items[position]
        holder.bind(character)

        holder.itemView.setOnClickListener {
            onItemClick(character)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(data: List<CharacterNaruto>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }
}
