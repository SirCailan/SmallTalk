package com.example.smalltalk.database

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM users LIMIT 1")
    fun getCurrentUser(): User

    @Query("SELECT * FROM users WHERE id =:userId LIMIT 1")
    fun getCurrentUserId(userId: Int) : User

    @Insert
    fun addUser(user: User)

    @Update
    fun updateCurrentUser(user: User)

    @Query("DELETE FROM users")
    fun deleteAllUsers()

    @Delete
    fun deleteUser(user: User)
}