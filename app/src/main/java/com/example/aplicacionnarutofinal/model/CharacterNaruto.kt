package com.example.aplicacionnarutofinal.model

import android.os.Parcel
import android.os.Parcelable

data class CharacterNaruto(
    val id: Int,
    val name: String?,
    val images: List<String>?,
    val debut: Debut?,
    val jutsu: List<String>?,
    val personal: Any?,
    val rank: Any?,
    val voiceActors: VoiceActors?,
    val family: Family?,
    val natureType: List<String>?,
    val uniqueTraits: List<String>?
) : Parcelable {
    val imageUrl: String?
        get() = images?.firstOrNull()

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readParcelable(Debut::class.java.classLoader),
        parcel.createStringArrayList(),
        // Leer otros campos de CharacterNaruto
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
        // Escribir otros campos de CharacterNaruto
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
    val novel: String?,
    val manga: String?,
    val movie: String?,
    val anime: String?,
    val game: String?,
    val ova: String?,
    val appearsIn: String?
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
    val english: Any?
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
    val incarnationWithTheGodTree: String?,
    val depoweredForm: String?
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
    val characters: List<CharacterNaruto>?
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
