package com.vincent.givetakeadmin.ui.activity.otp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vincent.givetakeadmin.data.repository.admin.AdminRepository
import com.vincent.givetakeadmin.data.source.request.AdminOtpRequest
import com.vincent.givetakeadmin.data.source.response.StatusResponse
import com.vincent.givetakeadmin.preference.UserPreferences
import com.vincent.givetakeadmin.utils.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class OtpAdminViewModel(private val repository: AdminRepository, private val userPreferences: UserPreferences) : ViewModel() {
    val otpAdminResult = MutableLiveData<Result<StatusResponse?>>()

    fun sendOtpAdmin(body: AdminOtpRequest) = viewModelScope.launch {
        repository.sendOtpAdmin(body).collect {
            otpAdminResult.value = it
        }
    }

    fun setLogged() = viewModelScope.launch {
        userPreferences.saveUserStatus(true)
    }
}