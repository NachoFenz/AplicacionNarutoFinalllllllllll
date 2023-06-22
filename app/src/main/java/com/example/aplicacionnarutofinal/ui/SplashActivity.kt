package com.example.aplicacionnarutofinal.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacionnarutofinal.R

class SplashActivity : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 2000 // Tiempo de duración de la pantalla de splash en milisegundos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Iniciar la actividad principal después de un tiempo de retardo
        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish() // Cerrar la actividad actual después de iniciar la actividad principal
        }, SPLASH_DELAY)
    }
}