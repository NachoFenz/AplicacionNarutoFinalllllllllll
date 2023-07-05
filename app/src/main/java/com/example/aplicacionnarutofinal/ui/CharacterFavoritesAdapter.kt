import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aplicacionnarutofinal.R
import com.example.aplicacionnarutofinal.model.CharacterNaruto

class CharacterFavoritesAdapter : RecyclerView.Adapter<CharacterFavoritesAdapter.CharacterViewHolder>() {

    private var characters: List<CharacterNaruto> = emptyList()
    private var itemClickListener: OnItemClickListener? = null

    // Establece la lista de personajes favoritos
    fun setCharacters(characters: List<CharacterNaruto>) {
        this.characters = characters
        notifyDataSetChanged()
    }

    // Establece el listener para los clicks en los elementos del adaptador
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        // Infla el diseño de la vista de elemento del adaptador
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_character, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        // Vincula los datos del personaje con las vistas de la interfaz de usuario en la posición dada
        val character = characters[position]
        holder.bind(character)
    }

    override fun getItemCount(): Int {
        // Devuelve el número de elementos en la lista de personajes
        return characters.size
    }

    inner class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgCharacter: ImageView = itemView.findViewById(R.id.imgCharacter)
        private val lblName: TextView = itemView.findViewById(R.id.lblName)

        init {
            // Configura el listener de clic en el elemento del adaptador
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val character = characters[position]
                    itemClickListener?.onItemClick(character)
                }
            }
        }

        fun bind(character: CharacterNaruto) {
            // Vincula los datos del personaje a las vistas de la interfaz de usuario en el elemento del adaptador
            lblName.text = character.name

            character.imageUrl?.let { imageUrl ->
                Glide.with(itemView)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(imgCharacter)
            } ?: run {
                imgCharacter.setImageResource(R.drawable.error_image)
            }
        }
    }

    // Interfaz para el listener de clic en los elementos del adaptador
    interface OnItemClickListener {
        fun onItemClick(character: CharacterNaruto)
    }
}
