package com.example.mvvmsample

import android.app.Application
import com.facebook.stetho.Stetho

class AppController:Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this);
    }
}