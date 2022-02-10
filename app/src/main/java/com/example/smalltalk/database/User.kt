package com.example.smalltalk.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "user_alias") var userName: String,
    @ColumnInfo(name = "user_first_name") val firstName: String,
    @ColumnInfo(name = "user_last_name") val lastName: String
)