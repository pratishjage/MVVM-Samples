package com.example.mvvmsample.Repos

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mvvmsample.Networks.ApiCalls
import com.example.mvvmsample.Networks.ApiService
import com.example.mvvmsample.Utils.Resource
import com.example.mvvmsample.db.User
import com.example.mvvmsample.db.UserDao
import com.example.mvvmsample.db.UsersDatabase
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.withContext

class MainRepo constructor(val application: Application) {
    private var apiCalls: ApiCalls
    private var userDao: UserDao

    init {
        apiCalls = ApiService.createService(ApiCalls::class.java)
        val db = UsersDatabase.getInstance(application.applicationContext)
        userDao = db.userDao()
    }


    suspend fun getUsers(): MutableLiveData<Resource<List<User>>> {

        val result: MutableLiveData<Resource<List<User>>> = MutableLiveData()
        val allUsers = userDao.getAllUsers()
        if ((allUsers.size > 0)) {
            result.value = Resource.success(allUsers)
            Log.d("getUsers", "local")
        } else {
            try {
                val response = withContext(Dispatchers.IO) {
                    apiCalls.getUsers()
                }
                try {
                    if (response.isSuccessful) {
                        var resp = response.body()
                        Log.d("getUsers", resp?.get(0)?.name)
                        withContext(Dispatchers.IO) {
                            resp?.forEach {
                                userDao.insertUser(it)
                            }
                        }
                        result.value = Resource.success(resp?.let { it })
                        Log.d("getUsers", "server")
                    } else {
                        result.postValue(Resource.error(java.lang.Exception()))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    result.value = Resource.error(e)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                result.value = Resource.error(e)
            }
            }
            return result
        }


        suspend fun updateUser(user: User) {
            withContext(Dispatchers.IO) {
                userDao.updateUser(user)
            }

        }

    }