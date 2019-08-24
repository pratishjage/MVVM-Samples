package com.example.mvvmsample.Repos

import android.app.Application
import android.util.Log
import com.example.mvvmsample.Networks.ApiCalls
import com.example.mvvmsample.Networks.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainRepo constructor(val application: Application) {
    private var apiCalls: ApiCalls

    init {
        apiCalls = ApiService().createService(ApiCalls::class.java)

    }


    fun getUsers() {
        GlobalScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    apiCalls.getUsers()
                }
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            var resp = response.body()
                            Log.d("getUsers", resp?.get(0)?.name)
                        } else {
                            //    result.postValue(Resource.error(java.lang.Exception()))
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        //  result.postValue(Resource.error(e))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // result.postValue(Resource.error(e))
            }
        }
    }
}