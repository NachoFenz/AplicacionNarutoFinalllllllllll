package com.example.aplicacionnarutofinal.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionnarutofinal.R
import com.example.aplicacionnarutofinal.model.CharacterNaruto

class CharactersNarutoAdapter(private val onItemClick: (CharacterNaruto) -> Unit) : RecyclerView.Adapter<CharacterViewHolder>() {

    // Lista de personajes de Naruto
    private var items: MutableList<CharacterNaruto> = ArrayList()

    // Crea una nueva vista de elemento y la infla
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_characternaruto, parent, false)
        return CharacterViewHolder(view)
    }

    // Vincula los datos de un personaje a la vista de un elemento
    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = items[position]
        holder.bind(character)

        // Configura un listener de clic en el elemento
        holder.itemView.setOnClickListener {
            onItemClick(character)
        }
    }

    // Devuelve la cantidad de elementos en la lista
    override fun getItemCount(): Int {
        return items.size
    }

    // Establece los datos de la lista de personajes
    fun setItems(data: List<CharacterNaruto>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }
}
