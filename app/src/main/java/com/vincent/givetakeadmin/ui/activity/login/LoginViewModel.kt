package com.vincent.givetakeadmin.ui.activity.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vincent.givetakeadmin.data.repository.admin.AdminRepository
import com.vincent.givetakeadmin.data.source.request.AdminLoginRequest
import com.vincent.givetakeadmin.data.source.response.admin.AdminLoginResponse
import com.vincent.givetakeadmin.utils.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel(private val adminRepository: AdminRepository) : ViewModel() {
    val loginAdminResult = MutableLiveData<Result<AdminLoginResponse?>>()

    fun loginAdmin(body: AdminLoginRequest) = viewModelScope.launch {
        adminRepository.loginAdmin(body).collect {
            loginAdminResult.value = it
        }
    }
}