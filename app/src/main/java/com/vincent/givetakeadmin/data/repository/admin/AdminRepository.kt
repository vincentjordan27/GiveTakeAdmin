package com.vincent.givetakeadmin.data.repository.admin

import com.google.gson.Gson
import com.vincent.givetakeadmin.data.repository.advice.AdviceRepository
import com.vincent.givetakeadmin.data.source.api.AdminService
import com.vincent.givetakeadmin.data.source.api.AdviceService
import com.vincent.givetakeadmin.data.source.request.AdminLoginRequest
import com.vincent.givetakeadmin.data.source.request.AdminOtpRequest
import com.vincent.givetakeadmin.data.source.response.StatusResponse
import com.vincent.givetakeadmin.data.source.response.admin.AdminLoginResponse
import com.vincent.givetakeadmin.data.source.response.reward.AllRewardsResponse
import com.vincent.givetakeadmin.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AdminRepository(private val apiService: AdminService) {

    fun loginAdmin(body: AdminLoginRequest) = flow {
        emit(Result.Loading)
        val response = apiService.loginAdmin(body)
        if (response.isSuccessful) {
            emit(Result.Success(response.body()))
        } else {
            val errorResponse = Gson().fromJson(response.errorBody()!!.string(), AdminLoginResponse::class.java)
            emit(Result.Error(errorResponse.message))
        }
    }.catch { emit(Result.Error("Server timeout. Silahkan dicoba kembali beberapa saat lagi"))
    }.flowOn(Dispatchers.IO)

    fun sendOtpAdmin(body: AdminOtpRequest) = flow {
        emit(Result.Loading)
        val response = apiService.sendOtpAdmin(body)
        if (response.isSuccessful) {
            emit(Result.Success(response.body()))
        } else {
            val errorResponse = Gson().fromJson(response.errorBody()!!.string(), StatusResponse::class.java)
            emit(Result.Error(errorResponse.message))
        }
    }.catch { emit(Result.Error("Server timeout. Silahkan dicoba kembali beberapa saat lagi"))
    }.flowOn(Dispatchers.IO)

    companion object {
        @Volatile
        private var instance: AdminRepository? = null

        fun getInstance(
            apiService: AdminService
        ) : AdminRepository {
            return instance ?: synchronized(this) {
                AdminRepository(apiService).also {
                    instance = it
                }
            }
        }
    }

}