package com.vincent.givetakeadmin.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vincent.givetakeadmin.data.repository.admin.AdminRepository
import com.vincent.givetakeadmin.data.repository.advice.AdviceRepository
import com.vincent.givetakeadmin.di.Injection
import com.vincent.givetakeadmin.ui.activity.login.LoginViewModel
import com.vincent.givetakeadmin.ui.activity.otp.OtpAdminViewModel
import com.vincent.givetakeadmin.ui.fragment.advice.AdviceViewModel

class AdminRepoViewModelFactory(private val repository: AdminRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: AdminRepoViewModelFactory? = null

        fun getInstance(): AdminRepoViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: AdminRepoViewModelFactory(Injection.provideAdminRepository())
            }.also { instance = it }
    }
}