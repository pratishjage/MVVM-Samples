package com.example.mvvmsample.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvmsample.Utils.SingletonHolder

@Database(entities = arrayOf(User::class), version = 1)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object : SingletonHolder<UsersDatabase, Context>({
        Room.databaseBuilder(
            it.applicationContext,
            UsersDatabase::class.java, "users.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    })
}