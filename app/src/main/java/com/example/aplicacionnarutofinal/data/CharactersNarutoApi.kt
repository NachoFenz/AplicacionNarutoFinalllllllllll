package com.example.aplicacionnarutofinal.data

import com.example.aplicacionnarutofinal.model.CharacterNaruto
import com.example.aplicacionnarutofinal.model.CharactersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersNarutoApi {
    @GET("/character")
    fun getCharactersNaruto(
        @Query("limit") limit: Int
    ): Call<CharactersResponse>
}