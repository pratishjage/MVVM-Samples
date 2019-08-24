package com.example.mvvmsample.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Query("select * from user")
    suspend fun getAllUsers(): List<User>

    @Insert
    suspend fun insertUser(users: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)
}