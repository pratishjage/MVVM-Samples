package com.example.mvvmsample.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.example.mvvmsample.Repos.MainRepo
import com.example.mvvmsample.Utils.Resource
import com.example.mvvmsample.db.User
import kotlinx.coroutines.launch

class PageViewModel(application: Application) : AndroidViewModel(application) {

    private val mainRepo: MainRepo

    init {
        mainRepo = MainRepo(application)
        getUsers()
    }

    private val mUsers = MediatorLiveData<Resource<List<User>>>()
    val users: MutableLiveData<Resource<List<User>>> = mUsers
    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }


    fun getUsers() {
        viewModelScope.launch {
            users.value = Resource.loading(null)
            users.value = mainRepo.getUsers().value
        }
    }

    fun getUsers(isFav: Boolean): LiveData<Resource<List<User>>> = users
}