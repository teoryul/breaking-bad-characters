package com.teodyulgerov.breakingbadcharacters.api.model

import com.google.gson.annotations.SerializedName

/**
 * Model class for handling api responses.
 */
data class CharacterResponse(
    @SerializedName("char_id")
    val charId: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("img")
    val img: String,

    @SerializedName("occupation")
    val occupation: List<String>,

    @SerializedName("status")
    val status: String,

    @SerializedName("nickname")
    val nickname: String,

    /**
     * Breaking Bad and/or Better Call Saul. We only look for Breaking Bad.
     */
    @SerializedName("category")
    val category: String,

    /**
     * Breaking Bad Season appearance.
     */
    @SerializedName("appearance")
    val appearance: List<Int>
)