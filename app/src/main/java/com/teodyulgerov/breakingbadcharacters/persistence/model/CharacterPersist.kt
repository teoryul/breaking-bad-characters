package com.teodyulgerov.breakingbadcharacters.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Model class for storing/retrieving api responses in the local database.
 */
@Entity(tableName = "character_table")
data class CharacterPersist(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "char_id")
    val char_id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "img")
    val img: String,

    @ColumnInfo(name = "occupation")
    val occupation: String,

    @ColumnInfo(name = "status")
    val status: String,

    @ColumnInfo(name = "nickname")
    val nickname: String,

    @ColumnInfo(name = "appearance")
    val appearance: String
)