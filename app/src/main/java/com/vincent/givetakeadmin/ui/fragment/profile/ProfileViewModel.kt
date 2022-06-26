package com.vincent.givetakeadmin.ui.fragment.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vincent.givetakeadmin.preference.UserPreferences
import kotlinx.coroutines.launch

class ProfileViewModel(private val preferences: UserPreferences) : ViewModel() {
    fun logout() = viewModelScope.launch {
        preferences.logout()
    }
}