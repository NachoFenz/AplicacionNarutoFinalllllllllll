package com.example.aplicacionnarutofinal.data

import android.util.Log
import com.example.aplicacionnarutofinal.model.CharacterNaruto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharactersNarutoDataSource {
    private val BASE_URL = "https://api.narutodb.xyz/"
    private val _TAG = "Apidemo"

    suspend fun getCharactersNaruto( limit: Int): List<CharacterNaruto> {
        Log.d(_TAG, "CharacterNarutoDataSourceGet")

        return withContext(Dispatchers.IO) {
            try {
                val api = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(CharactersNarutoApi::class.java)

                val response = api.getCharactersNaruto(limit).execute()

                if (response.isSuccessful) {
                    val charactersResponse = response.body()
                    charactersResponse?.characters ?: emptyList()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = if (errorBody.isNullOrEmpty()) {
                        response.message()
                    } else {
                        errorBody
                    }
                    Log.e(_TAG, "Error en el llamado api: $errorMessage")
                    emptyList()
                }
            } catch (e: Exception) {
                Log.e(_TAG, "Error en el llamado api: ${e.message}")
                emptyList()
            }
        }
    }
}