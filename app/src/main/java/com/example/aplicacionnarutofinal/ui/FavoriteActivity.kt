package com.example.aplicacionnarutofinal.ui

import CharacterFavoritesAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionnarutofinal.R
import com.example.aplicacionnarutofinal.model.CharacterNaruto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FavoriteActivity : AppCompatActivity(), CharacterFavoritesAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var characterAdapter: CharacterFavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        val btnRight: Button = findViewById(R.id.btnRight)
        val btnLeft: Button = findViewById(R.id.btnLeft)

        recyclerView = findViewById(R.id.recyclerViewCharacters)
        characterAdapter = CharacterFavoritesAdapter()
        characterAdapter.setOnItemClickListener(this)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = characterAdapter
        }

        getFavoriteCharactersFromFirebase()

        btnLeft.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnRight.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }
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

    override fun onItemClick(character: CharacterNaruto) {
        val intent = Intent(this, FavoriteCharacterDetailsActivity::class.java)
        intent.putExtra("characterNaruto", character)
        startActivity(intent)
    }
}
