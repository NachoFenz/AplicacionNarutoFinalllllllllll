package com.example.aplicacionnarutofinal.ui

import MainViewModel
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionnarutofinal.R
import com.example.aplicacionnarutofinal.model.CharacterNaruto
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var rvCharactersNaruto: RecyclerView
    private lateinit var adapter: CharactersNarutoAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: CustomProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth

        progressDialog = CustomProgressDialog(this) // Inicialización de progressDialog

        bindViews()
        bindViewModel()
    }

    override fun onStart() {
        super.onStart()
        progressDialog.show()
        viewModel.onStart()
    }

    private fun bindViews() {
        rvCharactersNaruto = findViewById(R.id.rvCharactersNaruto)
        rvCharactersNaruto.layoutManager = LinearLayoutManager(this)
        adapter = CharactersNarutoAdapter(::onCharacterClick)
        rvCharactersNaruto.adapter = adapter
        val btnRight: Button = findViewById(R.id.btnRight)
        val btnLeft: Button = findViewById(R.id.btnLeft)

        val btnLogout: Button = findViewById(R.id.btnLogout)
        btnLogout.setOnClickListener {
            signOut()
        }
        btnLeft.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnRight.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }

        val searchView: SearchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterCharacters(newText ?: "")
                return true
            }
        })
    }

    private fun bindViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.characters.observe(this) { characters ->
            adapter.setItems(characters)
            progressDialog.dismiss()
        }
    }

    private fun onCharacterClick(character: CharacterNaruto) {
        val intent = Intent(this, CharacterDetailsActivity::class.java)
        intent.putExtra("characterNaruto", character)
        startActivity(intent)
    }

    private fun signOut() {
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
