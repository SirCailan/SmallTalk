package com.example.smalltalk.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "userName") val userName: String,
    @ColumnInfo(name = "firstName") val firstName: String
)