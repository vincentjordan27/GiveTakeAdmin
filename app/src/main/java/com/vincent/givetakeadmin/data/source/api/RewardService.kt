package com.vincent.givetakeadmin.data.source.api

import com.vincent.givetakeadmin.data.source.request.AddUpdateRewardRequest
import com.vincent.givetakeadmin.data.source.response.StatusResponse
import com.vincent.givetakeadmin.data.source.response.request.AllRewardsRequestResponse
import com.vincent.givetakeadmin.data.source.response.reward.AllRewardsResponse
import com.vincent.givetakeadmin.data.source.response.reward.RewardDetailResponse
import com.vincent.givetakeadmin.data.source.response.reward.RewardItem
import com.vincent.givetakeadmin.data.source.response.reward.UploadImageRewardResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface RewardService {
    @GET("rewards")
    suspend fun getAllRewards() : Response<AllRewardsResponse>

    @GET("rewardreq")
    suspend fun getAllRequestRewards() : Response<AllRewardsRequestResponse>

    @GET("reward/{id}")
    suspend fun getRewardById(
        @Path("id") id: String
    ) : Response<RewardDetailResponse>

    @POST("rewards")
    suspend fun addReward(
        @Body body : AddUpdateRewardRequest
    ) : Response<StatusResponse>

    @PATCH("reward/{id}")
    suspend fun updateReward(
        @Path("id") id: String,
        @Body body: AddUpdateRewardRequest
    ) : Response<StatusResponse>

    @Multipart
    @POST("upload/reward/image")
    suspend fun uploadRewardImage(
        @Part data : MultipartBody.Part
    ) : Response<UploadImageRewardResponse>

    @DELETE("reward/{id}")
    suspend fun deleteReward(
        @Path("id") id: String,
    ) : Response<StatusResponse>
}