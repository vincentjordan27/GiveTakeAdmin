package com.vincent.givetakeadmin.ui.activity.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.vincent.givetakeadmin.preference.UserPreferences

class SplashViewModel(private val preferences: UserPreferences) : ViewModel() {
    fun getUserAccessKey() : LiveData<String> {
        return preferences.getUserAccessKey().asLiveData()
    }
}
