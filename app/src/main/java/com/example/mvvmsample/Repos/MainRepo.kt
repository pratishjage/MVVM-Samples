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
import kotlin.math.log

class MainRepo constructor(val application: Application) {
    private var apiCalls: ApiCalls
    private var userDao: UserDao
    private var mainList: MutableLiveData<Resource<List<User>>>

    init {
        apiCalls = ApiService.createService(ApiCalls::class.java)
        val db = UsersDatabase.getInstance(application.applicationContext)
        userDao = db.userDao()
        mainList = MutableLiveData()
        mainList.value = Resource.loading(null)
    }


    suspend fun getUsers(): MutableLiveData<Resource<List<User>>> {


        val allUsers = userDao.getAllUsers()
        if ((allUsers.size > 0)) {
            mainList.value = Resource.success(allUsers)
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
                        mainList.value = Resource.success(resp?.let { it })
                        Log.d("getUsers", "server")
                    } else {
                        mainList.postValue(Resource.error(java.lang.Exception()))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    mainList.value = Resource.error(e)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                mainList.value = Resource.error(e)
            }
        }
        return mainList
    }

    suspend fun updateUser(user: User): MutableLiveData<Resource<List<User>>> {
        withContext(Dispatchers.IO) {
            userDao.updateUser(user)
        }
        mainList.value = Resource.success(userDao.getAllUsers())
        return mainList
    }
}