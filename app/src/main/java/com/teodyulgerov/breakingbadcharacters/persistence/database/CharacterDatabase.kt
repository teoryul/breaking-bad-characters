package com.teodyulgerov.breakingbadcharacters.persistence.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.teodyulgerov.breakingbadcharacters.persistence.dao.CharacterDao
import com.teodyulgerov.breakingbadcharacters.persistence.model.CharacterPersist

@Database(
    entities = [
        CharacterPersist::class
    ],
    version = 1,
    exportSchema = false
)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}