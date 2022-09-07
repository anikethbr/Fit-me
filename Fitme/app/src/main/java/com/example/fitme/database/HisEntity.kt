package com.example.fitme.database

import androidx.room.ColumnInfo
import androidx.room.Entity

//Entity class for the history database
@Entity(tableName = "history", primaryKeys = ["his_date", "his_area"])
data class HisEntity(
    @ColumnInfo(name = "his_date") val hisDate: String,
    @ColumnInfo(name = "his_area") val hisArea: String
)