package com.example.mvvmsample.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.example.mvvmsample.Repos.MainRepo

class PageViewModel(application: Application) : AndroidViewModel(application) {

    private val mainRepo: MainRepo

    init {
        mainRepo = MainRepo(application)
        mainRepo.getUsers()
    }

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }

    fun getUsers() {
        mainRepo.getUsers()
    }
}