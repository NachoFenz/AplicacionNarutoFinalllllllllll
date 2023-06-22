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

    private var dialog: CustomDialog
    private var cpCardView: CardView
    private var imageView: ImageView

    fun show() {
        dialog.show()
        Glide.with(imageView)
            .asGif()
            .load(R.drawable.placeholder_image)
            .into(imageView)
    }

    fun dismiss() {
        dialog.dismiss()
    }

    init {
        val inflater = (context as Activity).layoutInflater
        val view = inflater.inflate(R.layout.activity_custom_progress_dialog, null)

        cpCardView = view.findViewById(R.id.cp_cardview)
        imageView = view.findViewById(R.id.cp_image)


        // Custom Dialog initialization
        dialog = CustomDialog(context)
        dialog.setContentView(view)
    }

    private fun setColorFilter(drawable: Drawable, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    class CustomDialog(context: Context) : Dialog(context) {
        init {
            // Set Semi-Transparent Color for Dialog Background
            window?.decorView?.setOnApplyWindowInsetsListener { _, insets ->
                insets.consumeSystemWindowInsets()
            }
        }
    }
}
