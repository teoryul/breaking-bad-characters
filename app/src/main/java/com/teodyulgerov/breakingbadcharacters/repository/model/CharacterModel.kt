package com.teodyulgerov.breakingbadcharacters.repository.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Model class for displaying data in the UI.
 */
@Parcelize
data class CharacterModel(
    val name: String,

    val img: String,

    val occupation: String,

    val status: String,

    val nickname: String,

    val appearance: String
) : Parcelable