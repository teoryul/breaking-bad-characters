package com.teodyulgerov.breakingbadcharacters.repository

import com.teodyulgerov.breakingbadcharacters.repository.model.CharacterModel
import com.teodyulgerov.breakingbadcharacters.repository.utils.RepoResult

interface CharacterRepository {
    suspend fun getCharacters(): RepoResult<List<CharacterModel>>
}