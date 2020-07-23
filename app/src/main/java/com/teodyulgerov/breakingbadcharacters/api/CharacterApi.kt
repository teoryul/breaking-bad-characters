package com.teodyulgerov.breakingbadcharacters.api

import com.teodyulgerov.breakingbadcharacters.api.model.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET

interface CharacterApi {
    @GET("characters")
    suspend fun getCharacters(): Response<List<CharacterResponse>>
}