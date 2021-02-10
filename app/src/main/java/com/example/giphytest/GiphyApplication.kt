package com.example.giphytest

import android.app.Application
import com.giphy.sdk.ui.Giphy

class GiphyApplication : Application() {
    val YOUR_API_KEY = "74M3W6tD2fiI2zXfIGrjVh8dcDs4AQzK"
    override fun onCreate() {
        super.onCreate()
        Giphy.configure(this, YOUR_API_KEY, true)
    }
}
