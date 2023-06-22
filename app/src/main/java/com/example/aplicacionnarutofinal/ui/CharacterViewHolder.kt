package com.example.aplicacionnarutofinal.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aplicacionnarutofinal.R
import com.example.aplicacionnarutofinal.model.CharacterNaruto

class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val lblName: TextView = itemView.findViewById(R.id.lblName)
    private val imgCharacter: ImageView = itemView.findViewById(R.id.imgCharacter)

    fun bind(character: CharacterNaruto) {
        lblName.text = character.name

        character.imageUrl?.let { imageUrl ->
            Glide.with(itemView.context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(imgCharacter)
        } ?: run {
            imgCharacter.setImageResource(R.drawable.error_image)
        }
    }
}
