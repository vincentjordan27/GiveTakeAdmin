package com.vincent.givetakeadmin.data.repository.advice

import com.google.gson.Gson
import com.vincent.givetakeadmin.data.repository.reward.RewardRepository
import com.vincent.givetakeadmin.data.source.api.AdviceService
import com.vincent.givetakeadmin.data.source.api.RewardService
import com.vincent.givetakeadmin.data.source.request.ReplyAdviceRequest
import com.vincent.givetakeadmin.data.source.response.StatusResponse
import com.vincent.givetakeadmin.data.source.response.reward.AllRewardsResponse
import com.vincent.givetakeadmin.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AdviceRepository(private val apiService: AdviceService) {

    fun getAllAdvices() = flow {
        emit(Result.Loading)
        val response = apiService.getAllAdvices()
        if (response.isSuccessful) {
            emit(Result.Success(response.body()))
        } else {
            val errorResponse = Gson().fromJson(response.errorBody()!!.string(), AllRewardsResponse::class.java)
            emit(Result.Error(errorResponse.message))
        }
    }.catch { emit(Result.Error("Server timeout. Silahkan dicoba kembali beberapa saat lagi"))
    }.flowOn(Dispatchers.IO)

    fun replyAdvice(id: String, body: ReplyAdviceRequest) = flow {
        emit(Result.Loading)
        val response = apiService.replyAdvice(id, body)
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
        private var instance: AdviceRepository? = null

        fun getInstance(
            apiService: AdviceService
        ) : AdviceRepository {
            return instance ?: synchronized(this) {
                AdviceRepository(apiService).also {
                    instance = it
                }
            }
        }
    }
}