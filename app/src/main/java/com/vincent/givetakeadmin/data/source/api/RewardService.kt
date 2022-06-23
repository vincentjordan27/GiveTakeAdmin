package com.vincent.givetakeadmin.data.source.api

import com.vincent.givetakeadmin.data.source.response.reward.AllRewardsResponse
import retrofit2.Response
import retrofit2.http.GET

interface RewardService {
    @GET("rewards")
    suspend fun getAllRewards() : Response<AllRewardsResponse>
}