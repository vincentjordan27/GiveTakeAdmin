package com.vincent.givetakeadmin.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vincent.givetakeadmin.data.repository.admin.AdminRepository
import com.vincent.givetakeadmin.data.repository.advice.AdviceRepository
import com.vincent.givetakeadmin.di.Injection
import com.vincent.givetakeadmin.preference.UserPreferences
import com.vincent.givetakeadmin.ui.activity.login.LoginViewModel
import com.vincent.givetakeadmin.ui.activity.otp.OtpAdminViewModel
import com.vincent.givetakeadmin.ui.fragment.advice.AdviceViewModel

class PrefAdminRepoViewModelFactory(private val repository: AdminRepository, private val userPreferences: UserPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OtpAdminViewModel::class.java)) {
            return OtpAdminViewModel(repository, userPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: PrefAdminRepoViewModelFactory? = null

        fun getInstance(preferences: UserPreferences): PrefAdminRepoViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: PrefAdminRepoViewModelFactory(Injection.provideAdminRepository(), preferences)
            }.also { instance = it }
    }
}