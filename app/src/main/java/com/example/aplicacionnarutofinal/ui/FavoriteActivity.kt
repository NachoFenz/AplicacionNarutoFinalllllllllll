package com.example.aplicacionnarutofinal.ui
import CharacterFavoritesAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionnarutofinal.R
import com.example.aplicacionnarutofinal.model.CharacterNaruto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FavoriteActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var characterAdapter: CharacterFavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        recyclerView = findViewById(R.id.recyclerViewCharacters)
        characterAdapter = CharacterFavoritesAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = characterAdapter
        }

        getFavoriteCharactersFromFirebase()
    }

    private fun getFavoriteCharactersFromFirebase() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null) {
            val db = FirebaseFirestore.getInstance()
            val favoritesCollection = db.collection("users").document(userId).collection("favorites")

            favoritesCollection.get().addOnSuccessListener { snapshot ->
                val favoriteCharacters = mutableListOf<CharacterNaruto>()

                for (document in snapshot.documents) {
                    val character = document.toObject(CharacterNaruto::class.java)
                    character?.let {
                        favoriteCharacters.add(it)
                    }
                }

                characterAdapter.setCharacters(favoriteCharacters)
            }.addOnFailureListener { exception ->
                // Manejo de errores
            }
        }
    }
}
