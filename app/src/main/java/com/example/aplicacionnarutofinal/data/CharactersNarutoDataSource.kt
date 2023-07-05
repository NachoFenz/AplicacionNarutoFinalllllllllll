package com.example.aplicacionnarutofinal.data

import android.util.Log
import com.example.aplicacionnarutofinal.model.CharacterNaruto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import android.content.Context
import androidx.room.Room
import com.example.aplicacionnarutofinal.data.local.AppDatabase
import com.example.aplicacionnarutofinal.data.local.CachedCharacter

class CharactersNarutoDataSource(context: Context) {
    private val BASE_URL = "https://api.narutodb.xyz/"
    private val _TAG = "Apidemo"
    private val db: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java, "characters-database"
    ).build()

    suspend fun getCharactersNaruto(limit: Int): List<CharacterNaruto> {
        Log.d(_TAG, "CharacterNarutoDataSourceGet")

        // Intenta obtener los datos de la caché primero
        val cachedCharacters = db.characterDao().getCachedCharacters()
        if (cachedCharacters.isNotEmpty()) {
            return cachedCharacters.map { character ->
                CharacterNaruto(character.id, character.name)
                // Mapea los otros campos necesarios
            }
        }

        // Si no hay datos en la caché, realiza la llamada a la API
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
                    val characters = charactersResponse?.characters ?: emptyList()

                    // Guarda los datos en la caché
                    val cachedCharacters = characters.map { character ->
                        CachedCharacter(character.id, character.name)
                        // Mapea los otros campos necesarios
                    }
                    db.characterDao().insertCachedCharacters(cachedCharacters)

                    characters
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

    suspend fun clearCachedCharacters() {
        withContext(Dispatchers.IO) {
            db.characterDao().clearCachedCharacters()
        }
    }
}