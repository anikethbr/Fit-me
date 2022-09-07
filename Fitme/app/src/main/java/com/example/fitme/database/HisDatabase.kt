package com.example.fitme.database

import androidx.room.Database
import androidx.room.RoomDatabase

//Database class for the history database
@Database(entities = [HisEntity::class], version = 1)
abstract class HisDatabase : RoomDatabase() {
    abstract fun hisDao(): HisDao
}