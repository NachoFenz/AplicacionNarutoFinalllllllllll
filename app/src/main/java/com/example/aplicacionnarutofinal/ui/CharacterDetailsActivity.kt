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
import com.example.aplicacionnarutofinal.ui.LoginActivity
import com.example.aplicacionnarutofinal.ui.MainActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class CharacterDetailsActivity : AppCompatActivity() {

    private lateinit var character: CharacterNaruto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)

        character = intent.getParcelableExtra("characterNaruto")!!

        bindViews()
        displayCharacterDetails()
    }

    private fun bindViews() {
        val lblName: TextView = findViewById(R.id.lblName)
        val lblDebut: TextView = findViewById(R.id.lblDebut)
        val imgCharacter: ImageView = findViewById(R.id.imgCharacter)
        val btnLeft: Button = findViewById(R.id.btnLeft)
        val btnLogout: Button = findViewById(R.id.btnLogout)

        btnLeft.setOnClickListener {
            // Redirigir al MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnLogout.setOnClickListener {
            // Cerrar la sesión de Google
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .build()
            val googleSignInClient = GoogleSignIn.getClient(this, gso)
            googleSignInClient.signOut().addOnCompleteListener {
                // Cerrar sesión en Firebase (si está utilizando Firebase Authentication)
                FirebaseAuth.getInstance().signOut()

                // Redirigir a la pantalla de inicio de sesión
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun displayCharacterDetails() {
        val lblName: TextView = findViewById(R.id.lblName)
        val lblDebut: TextView = findViewById(R.id.lblDebut)
        val imgCharacter: ImageView = findViewById(R.id.imgCharacter)
        // Display other details as needed

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
}
