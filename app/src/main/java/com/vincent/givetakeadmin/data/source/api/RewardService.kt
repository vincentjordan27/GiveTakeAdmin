package com.vincent.givetakeadmin.data.source.api

import com.vincent.givetakeadmin.data.source.request.AddRewardRequest
import com.vincent.givetakeadmin.data.source.response.StatusResponse
import com.vincent.givetakeadmin.data.source.response.request.AllRewardsRequestResponse
import com.vincent.givetakeadmin.data.source.response.reward.AllRewardsResponse
import com.vincent.givetakeadmin.data.source.response.reward.UploadImageRewardResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface RewardService {
    @GET("rewards")
    suspend fun getAllRewards() : Response<AllRewardsResponse>

    @GET("rewardreq")
    suspend fun getAllRequestRewards() : Response<AllRewardsRequestResponse>

    @POST("rewards")
    suspend fun addReward(
        @Body body : AddRewardRequest
    ) : Response<StatusResponse>

    @Multipart
    @POST("upload/reward/image")
    suspend fun uploadRewardImage(
        @Part data : MultipartBody.Part
    ) : Response<UploadImageRewardResponse>
}