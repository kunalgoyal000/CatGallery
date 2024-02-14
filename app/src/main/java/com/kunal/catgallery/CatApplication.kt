package com.kunal.catgallery

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CatApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Log.d(TAG, "Inside onCreate of Application")
        //Disable dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    companion object{
        const val TAG = "CatApplication"
    }
}