package com.example.aplicacionnarutofinal.ui

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.example.aplicacionnarutofinal.R

class CustomProgressDialog(context: Context) {

    // Propiedades privadas
    private var dialog: CustomDialog
    private var cpCardView: CardView
    private var imageView: ImageView

    // Método para mostrar el cuadro de diálogo
    fun show() {
        dialog.show()
        Glide.with(imageView)
            .asGif()
            .load(R.drawable.placeholder_image)
            .into(imageView)
    }

    // Método para ocultar el cuadro de diálogo
    fun dismiss() {
        dialog.dismiss()
    }

    init {
        // Inicialización del cuadro de diálogo personalizado
        val inflater = (context as Activity).layoutInflater
        val view = inflater.inflate(R.layout.activity_custom_progress_dialog, null)

        cpCardView = view.findViewById(R.id.cp_cardview)
        imageView = view.findViewById(R.id.cp_image)

        dialog = CustomDialog(context)
        dialog.setContentView(view)
    }

    // Método privado para aplicar un filtro de color a un Drawable
    private fun setColorFilter(drawable: Drawable, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    // Clase interna que representa el cuadro de diálogo personalizado
    class CustomDialog(context: Context) : Dialog(context) {
        init {
            // Establecer un color semitransparente para el fondo del cuadro de diálogo
            window?.decorView?.setOnApplyWindowInsetsListener { _, insets ->
                insets.consumeSystemWindowInsets()
            }
        }
    }
}
