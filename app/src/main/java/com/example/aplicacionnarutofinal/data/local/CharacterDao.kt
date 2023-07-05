package com.example.aplicacionnarutofinal.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {
    @Query("SELECT * FROM cached_characters")
    suspend fun getCachedCharacters(): List<CachedCharacter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCachedCharacters(characters: List<CachedCharacter>)

    @Query("DELETE FROM cached_characters")
    suspend fun clearCachedCharacters()
}
