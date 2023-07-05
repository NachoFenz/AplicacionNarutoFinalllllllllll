package com.example.aplicacionnarutofinal.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CachedCharacter::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}
