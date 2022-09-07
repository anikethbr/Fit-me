package com.example.fitme.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

//Dao interface for the history database
@Dao
interface HisDao {

    //    function to insert an entry to the database
    @Insert
    fun insert(hisEntity: HisEntity)

    //    retrieving all entries from the database
    @Query("Select * from history")
    fun getAll(): List<HisEntity>
}