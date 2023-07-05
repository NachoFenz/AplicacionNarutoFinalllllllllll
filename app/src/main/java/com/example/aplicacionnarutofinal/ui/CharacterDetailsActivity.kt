package com.example.aplicacionnarutofinal.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.aplicacionnarutofinal.R
import com.example.aplicacionnarutofinal.model.CharacterNaruto
import com.example.aplicacionnarutofinal.model.Debut
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CharacterDetailsActivity : AppCompatActivity() {

    private lateinit var character: CharacterNaruto
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)

        character = intent.getParcelableExtra("characterNaruto")!!

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        bindViews()
        displayCharacterDetails()
    }

    private fun bindViews() {
        val btnFavorito: Button = findViewById(R.id.btnfavoritos)
        val btnRight: Button = findViewById(R.id.btnRight)
        val btnLeft: Button = findViewById(R.id.btnLeft)

        btnFavorito.setOnClickListener {
            addCharacterToFavorites()
        }
        btnLeft.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        btnRight.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun displayCharacterDetails() {
        val lblName: TextView = findViewById(R.id.lblName)
        val lblDebut: TextView = findViewById(R.id.lblDebut)
        val imgCharacter: ImageView = findViewById(R.id.imgCharacter)
        // Mostrar otros detalles según sea necesario

        lblName.text = character.name

        character.debut?.let { debut ->
            val debutDetails = buildDebutDetails(debut)
            lblDebut.text = debutDetails
        }

        character.imageUrl?.let { imageUrl ->
            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(imgCharacter)
        } ?: run {
            imgCharacter.setImageResource(R.drawable.error_image)
        }
    }

    private fun buildDebutDetails(debut: Debut): String {
        val debutList = mutableListOf<String>()

        debut.anime?.let { debutList.add("Anime: $it") }
        debut.manga?.let { debutList.add("Manga: $it") }
        debut.novel?.let { debutList.add("Novel: $it") }
        debut.movie?.let { debutList.add("Movie: $it") }
        debut.game?.let { debutList.add("Game: $it") }
        debut.ova?.let { debutList.add("OVA: $it") }
        debut.appearsIn?.let { debutList.add("Appears In: $it") }

        return debutList.joinToString("\n")
    }

    private fun addCharacterToFavorites() {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            val userFavoritesRef = firestore.collection("users").document(userId)
                .collection("favorites")

            // Verificar si el personaje ya está en favoritos
            userFavoritesRef.document(character.id.toString()).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.exists()) {
                        // El personaje ya está en favoritos
                        // Aquí puedes mostrar un mensaje o realizar alguna acción
                    } else {
                        // Agregar el personaje a favoritos
                        userFavoritesRef.document(character.id.toString()).set(character)
                            .addOnCompleteListener { addTask ->
                                if (addTask.isSuccessful) {
                                    // El personaje se agregó a favoritos exitosamente
                                } else {
                                    // Error al agregar el personaje a favoritos
                                }
                            }
                    }
                } else {
                    // Error al verificar si el personaje está en favoritos
                }
            }
        }
    }
}
