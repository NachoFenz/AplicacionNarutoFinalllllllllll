package com.example.aplicacionnarutofinal.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aplicacionnarutofinal.R
import com.example.aplicacionnarutofinal.model.CharacterNaruto

class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    // Vistas de la interfaz de usuario
    private val lblName: TextView = itemView.findViewById(R.id.lblName)
    private val imgCharacter: ImageView = itemView.findViewById(R.id.imgCharacter)

    // Vincula los datos de un personaje a las vistas de la interfaz de usuario
    fun bind(character: CharacterNaruto) {
        // Establece el nombre del personaje
        lblName.text = character.name

        // Carga la imagen del personaje utilizando Glide
        character.imageUrl?.let { imageUrl ->
            Glide.with(itemView.context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder_image) // Imagen de marcador de posición mientras se carga
                .error(R.drawable.error_image) // Imagen de error si no se puede cargar la imagen
                .into(imgCharacter)
        } ?: run {
            // Si la URL de la imagen es nula, se establece una imagen de error
            imgCharacter.setImageResource(R.drawable.error_image)
        }
    }
}
