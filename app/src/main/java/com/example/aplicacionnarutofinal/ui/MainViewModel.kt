import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplicacionnarutofinal.data.CharactersNarutoRepository
import com.example.aplicacionnarutofinal.model.CharacterNaruto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

class MainViewModel : ViewModel() {
    private val coroutineContext: CoroutineContext = newSingleThreadContext("NarutoDemo")
    private val scope = CoroutineScope(coroutineContext)
    private val _TAG = "Apidemo"

    private val charactersNarutoRepo = CharactersNarutoRepository()

    val characters = MutableLiveData<ArrayList<CharacterNaruto>>()
    val limit = 1431

    fun onStart() {
        filterCharacters()
    }

    fun filterCharacters(searchQuery: String = "") {
        scope.launch {
            kotlin.runCatching {
                val characterList = charactersNarutoRepo.GetCharactersNaruto(limit)
                if (searchQuery.isBlank()) {
                    characterList
                } else {
                    characterList.filter { character ->
                        character.name?.contains(searchQuery, ignoreCase = true) == true
                    }
                }
            }.onSuccess { filteredList ->
                Log.d(_TAG, "CharactersNaruto OnSuccess")
                val characterArrayList = ArrayList(filteredList)
                characters.postValue(characterArrayList)
            }.onFailure { exception ->
                Log.e(_TAG, "CharactersNaruto ERROR: ${exception.message}", exception)
            }
        }
    }
}
