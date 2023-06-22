package com.example.aplicacionnarutofinal.data

import com.example.aplicacionnarutofinal.model.CharacterNaruto

class CharactersNarutoRepository {
    private val charactersNarutoDs = CharactersNarutoDataSource()

    suspend fun GetCharactersNaruto( limit: Int): List<CharacterNaruto> {
        return charactersNarutoDs.getCharactersNaruto(limit)
    }
}