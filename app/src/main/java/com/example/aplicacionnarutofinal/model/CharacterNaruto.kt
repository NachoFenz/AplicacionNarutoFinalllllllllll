package com.example.aplicacionnarutofinal.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class CharacterNaruto @JvmOverloads constructor(
    val id: Int = 0,
    val name: String? = null,
    val images: List<String>? = null,
    val debut: Debut? = null,
    val jutsu: List<String>? = null,
    @SerializedName("personal_data")
    val personal: Any? = null,
    val rank: Any? = null,
    val voiceActors: VoiceActors? = null,
    val family: Family? = null,
    val natureType: List<String>? = null,
    val uniqueTraits: List<String>? = null
) : Parcelable {
    // Propiedad calculada que devuelve la primera URL de imagen (si está disponible) del personaje
    val imageUrl: String?
        get() = images?.firstOrNull()
    // (El resto del código de la clase se refiere a la implementación Parcelable para permitir que los objetos de esta clase se pasen entre componentes de la app a través de Intents)

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readParcelable(Debut::class.java.classLoader),
        parcel.createStringArrayList(),
        parcel.readValue(Any::class.java.classLoader),
        parcel.readValue(Any::class.java.classLoader),
        parcel.readParcelable(VoiceActors::class.java.classLoader),
        parcel.readParcelable(Family::class.java.classLoader),
        parcel.createStringArrayList(),
        parcel.createStringArrayList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeStringList(images)
        parcel.writeParcelable(debut, flags)
        parcel.writeStringList(jutsu)
        parcel.writeValue(personal)
        parcel.writeValue(rank)
        parcel.writeParcelable(voiceActors, flags)
        parcel.writeParcelable(family, flags)
        parcel.writeStringList(natureType)
        parcel.writeStringList(uniqueTraits)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CharacterNaruto> {
        override fun createFromParcel(parcel: Parcel): CharacterNaruto {
            return CharacterNaruto(parcel)
        }

        override fun newArray(size: Int): Array<CharacterNaruto?> {
            return arrayOfNulls(size)
        }
    }
}

data class Debut(
    val novel: String? = null,
    val manga: String? = null,
    val movie: String? = null,
    val anime: String? = null,
    val game: String? = null,
    val ova: String? = null,
    val appearsIn: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(novel)
        parcel.writeString(manga)
        parcel.writeString(movie)
        parcel.writeString(anime)
        parcel.writeString(game)
        parcel.writeString(ova)
        parcel.writeString(appearsIn)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Debut> {
        override fun createFromParcel(parcel: Parcel): Debut {
            return Debut(parcel)
        }

        override fun newArray(size: Int): Array<Debut?> {
            return arrayOfNulls(size)
        }
    }
}

data class VoiceActors(
    val english: Any? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readValue(Any::class.java.classLoader))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(english)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VoiceActors> {
        override fun createFromParcel(parcel: Parcel): VoiceActors {
            return VoiceActors(parcel)
        }

        override fun newArray(size: Int): Array<VoiceActors?> {
            return arrayOfNulls(size)
        }
    }
}

data class Family(
    val incarnationWithTheGodTree: String? = null,
    val depoweredForm: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(incarnationWithTheGodTree)
        parcel.writeString(depoweredForm)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Family> {
        override fun createFromParcel(parcel: Parcel): Family {
            return Family(parcel)
        }

        override fun newArray(size: Int): Array<Family?> {
            return arrayOfNulls(size)
        }
    }
}

data class CharactersResponse(
    val characters: List<CharacterNaruto>? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(CharacterNaruto))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(characters)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CharactersResponse> {
        override fun createFromParcel(parcel: Parcel): CharactersResponse {
            return CharactersResponse(parcel)
        }

        override fun newArray(size: Int): Array<CharactersResponse?> {
            return arrayOfNulls(size)
        }
    }
}
