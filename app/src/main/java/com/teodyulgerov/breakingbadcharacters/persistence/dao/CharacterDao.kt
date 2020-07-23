package com.teodyulgerov.breakingbadcharacters.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import com.teodyulgerov.breakingbadcharacters.persistence.model.CharacterPersist

@Dao
interface CharacterDao : BaseDao<CharacterPersist> {
    @Query("SELECT * FROM character_table ORDER BY char_id ASC")
    fun getAllCharacters(): List<CharacterPersist>
}