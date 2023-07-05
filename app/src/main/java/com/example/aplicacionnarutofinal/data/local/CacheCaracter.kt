package com.example.aplicacionnarutofinal.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_characters")
data class CachedCharacter(
    @PrimaryKey val id: Int,
    val name: String?,
)