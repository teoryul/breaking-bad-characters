package com.teodyulgerov.breakingbadcharacters.injection.module.persistence

import android.content.Context
import androidx.room.Room
import com.teodyulgerov.breakingbadcharacters.persistence.dao.CharacterDao
import com.teodyulgerov.breakingbadcharacters.persistence.database.CharacterDatabase
import dagger.Module
import dagger.Provides

@Module
class RoomModule(context: Context) {

    private val characterDatabase: CharacterDatabase = Room
        .databaseBuilder(context, CharacterDatabase::class.java, "character_db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideCharacterDatabase(): CharacterDatabase {
        return characterDatabase
    }

    @Provides
    fun provideCharacterDao(): CharacterDao {
        return characterDatabase.characterDao()
    }
}