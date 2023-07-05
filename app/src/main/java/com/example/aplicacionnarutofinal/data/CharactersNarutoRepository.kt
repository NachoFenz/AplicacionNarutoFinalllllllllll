package com.example.aplicacionnarutofinal.data

import android.content.Context
import android.content.SharedPreferences
import com.example.aplicacionnarutofinal.model.CharacterNaruto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharactersNarutoRepository(context: Context) {
    private val charactersNarutoDs = CharactersNarutoDataSource(context)
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("characters_cache", Context.MODE_PRIVATE)

    suspend fun getCharactersNaruto(limit: Int): List<CharacterNaruto> {
        return charactersNarutoDs.getCharactersNaruto(limit)
    }

    fun clearCache() {
        CoroutineScope(Dispatchers.IO).launch {
            kotlin.runCatching {
                charactersNarutoDs.clearCachedCharacters()
            }.onFailure { exception ->
                // Manejar cualquier excepción que ocurra durante el borrado de la caché
            }
        }
    }
}
