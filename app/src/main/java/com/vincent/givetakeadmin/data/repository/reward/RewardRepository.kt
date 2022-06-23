package com.vincent.givetakeadmin.data.repository.reward

import com.google.gson.Gson
import com.vincent.givetakeadmin.data.source.api.RewardService
import com.vincent.givetakeadmin.data.source.response.reward.AllRewardsResponse
import com.vincent.givetakeadmin.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

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
//        emit(Result.Error("Server timeout. Silahkan dicoba kembali beberapa saat lagi"))
        emit(Result.Error(it.localizedMessage.toString()))
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