package com.example.animecompose

import android.app.Application
import com.example.animecompose.data.models.Impls.MovieModelImpl
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}