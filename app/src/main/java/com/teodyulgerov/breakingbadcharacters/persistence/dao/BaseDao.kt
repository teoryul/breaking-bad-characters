package com.teodyulgerov.breakingbadcharacters.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItemWithReplaceStrategy(t: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListWithReplaceStrategy(t: List<T>)

    @Delete
    fun deleteItem(t: T)
}