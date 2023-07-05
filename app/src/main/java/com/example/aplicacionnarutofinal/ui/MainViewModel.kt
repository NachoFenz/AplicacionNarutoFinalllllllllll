package com.example.aplicacionnarutofinal.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aplicacionnarutofinal.data.CharactersNarutoRepository
import com.example.aplicacionnarutofinal.model.CharacterNaruto
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val charactersNarutoRepo: CharactersNarutoRepository = CharactersNarutoRepository(application)
    private val _TAG = "Apidemo"
    val characters: MutableLiveData<ArrayList<CharacterNaruto>> = MutableLiveData()
    val limit = 1431

    fun onStart() {
        filterCharacters()
        charactersNarutoRepo.clearCache()
    }

    fun filterCharacters(searchQuery: String = "") {
        viewModelScope.launch {
            kotlin.runCatching {
                val characterList = charactersNarutoRepo.getCharactersNaruto(limit)
                val filteredList = if (searchQuery.isBlank()) {
                    characterList
                } else {
                    characterList.filter { character ->
                        character.name?.contains(searchQuery, ignoreCase = true) == true
                    }
                }
                Log.d(_TAG, "CharactersNaruto OnSuccess")
                val characterArrayList = ArrayList(filteredList)
                characters.postValue(characterArrayList)
            }.onFailure { exception ->
                Log.e(_TAG, "CharactersNaruto ERROR: ${exception.message}", exception)
            }
        }
    }
}
