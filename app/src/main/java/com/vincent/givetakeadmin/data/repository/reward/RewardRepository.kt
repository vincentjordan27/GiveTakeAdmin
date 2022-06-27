package com.vincent.givetakeadmin.data.repository.reward

import com.google.gson.Gson
import com.vincent.givetakeadmin.data.source.api.RewardService
import com.vincent.givetakeadmin.data.source.request.AddUpdateRewardRequest
import com.vincent.givetakeadmin.data.source.response.StatusResponse
import com.vincent.givetakeadmin.data.source.response.request.AllRewardsRequestResponse
import com.vincent.givetakeadmin.data.source.response.reward.AllRewardsResponse
import com.vincent.givetakeadmin.data.source.response.reward.RewardDetailResponse
import com.vincent.givetakeadmin.data.source.response.reward.UploadImageRewardResponse
import com.vincent.givetakeadmin.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class RewardRepository(private val apiService: RewardService) {

    fun getRewards() = flow {
        emit(Result.Loading)
        val response = apiService.getAllRewards()
        if (response.isSuccessful){
            emit(Result.Success(response.body()))
        }else {
            val errorResponse = Gson().fromJson(response.errorBody()!!.string(), AllRewardsResponse::class.java)
            emit(Result.Error(errorResponse.message))
        }
    }.catch {
        emit(Result.Error("Server timeout. Silahkan dicoba kembali beberapa saat lagi"))
    }.flowOn(Dispatchers.IO)

    fun getRewardDetail(id: String)  = flow {
        emit(Result.Loading)
        val response = apiService.getRewardById(id)
        if (response.isSuccessful) {
            emit(Result.Success(response.body()))
        } else {
            val errorResponse = Gson().fromJson(response.errorBody()!!.string(), RewardDetailResponse::class.java)
            emit(Result.Error(errorResponse.message))
        }
    }.catch {
        emit(Result.Error("Server timeout. Silahkan dicoba kembali beberapa saat lagi"))
    }.flowOn(Dispatchers.IO)

    fun getRewardsRequest() = flow {
        emit(Result.Loading)
        val response = apiService.getAllRequestRewards()
        if (response.isSuccessful) {
            emit(Result.Success(response.body()))
        } else {
            val errorResponse = Gson().fromJson(response.errorBody()!!.string(), AllRewardsRequestResponse::class.java)
            emit(Result.Error(errorResponse.message))
        }
    }.catch {
        emit(Result.Error("Server timeout. Silahkan dicoba kembali beberapa saat lagi"))
    }.flowOn(Dispatchers.IO)

    fun addReward(body: AddUpdateRewardRequest) = flow {
        emit(Result.Loading)
        val response = apiService.addReward(body)
        if (response.isSuccessful) {
            emit(Result.Success(response.body()))
        } else {
            val errorResponse = Gson().fromJson(response.errorBody()!!.string(), StatusResponse::class.java)
            emit(Result.Error(errorResponse.message))
        }
    }.catch {
        emit(Result.Error("Server timeout. Silahkan dicoba kembali beberapa saat lagi"))
    }.flowOn(Dispatchers.IO)

    fun updateReward(id: String, body: AddUpdateRewardRequest) = flow {
        emit(Result.Loading)
        val response = apiService.updateReward(id, body)
        if (response.isSuccessful) {
            emit(Result.Success(response.body()))
        } else {
            val errorResponse = Gson().fromJson(response.errorBody()!!.string(), StatusResponse::class.java)
            emit(Result.Error(errorResponse.message))
        }
    }.catch {
        emit(Result.Error("Server timeout. Silahkan dicoba kembali beberapa saat lagi"))
    }.flowOn(Dispatchers.IO)

    fun uploadImageReward(file: File) = flow {
        emit(Result.Loading)
        val requestBody = file.asRequestBody("image/*".toMediaType())
        val imageData = MultipartBody.Part.createFormData("data", filename = file.name, requestBody)
        val response = apiService.uploadRewardImage(imageData)
        if (response.isSuccessful) {
            emit(Result.Success(response.body()))
        } else {
            val errorResponse = Gson().fromJson(response.errorBody()!!.string(), UploadImageRewardResponse::class.java)
            emit(Result.Error(errorResponse.message))
        }
    }.catch {
        emit(Result.Error("Server timeout. Silahkan dicoba kembali beberapa saat lagi"))
    }.flowOn(Dispatchers.IO)

    fun deleteReward(id: String) = flow {
        emit(Result.Loading)
        val response = apiService.deleteReward(id)
        if (response.isSuccessful) {
            emit(Result.Success(response.body()))
        } else {
            val errorResponse = Gson().fromJson(response.errorBody()!!.string(), StatusResponse::class.java)
            emit(Result.Error(errorResponse.message))
        }
    }.catch {
        emit(Result.Error("Server timeout. Silahkan dicoba kembali beberapa saat lagi"))
    }.flowOn(Dispatchers.IO)

    fun finishRewardRequest(id: String) = flow {
        emit(Result.Loading)
        val response = apiService.finishRequestReward(id)
        if (response.isSuccessful) {
            emit(Result.Success(response.body()))
        } else {
            val errorResponse = Gson().fromJson(response.errorBody()!!.string(), StatusResponse::class.java)
            emit(Result.Error(errorResponse.message))
        }
    }.catch {
        emit(Result.Error("Server timeout. Silahkan dicoba kembali beberapa saat lagi"))
    }.flowOn(Dispatchers.IO)

    companion object {
        @Volatile
        private var instance: RewardRepository? = null

        fun getInstance(
            apiService: RewardService
        ) : RewardRepository {
            return instance ?: synchronized(this) {
                RewardRepository(apiService).also {
                    instance = it
                }
            }
        }
    }
}