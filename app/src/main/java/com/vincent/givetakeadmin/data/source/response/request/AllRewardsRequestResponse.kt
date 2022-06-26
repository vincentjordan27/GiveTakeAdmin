package com.vincent.givetakeadmin.data.source.response.request

data class AllRewardsRequestResponse(
    val status: String,
    val message: String,
    val data: List<RewardRequest>
)

data class RewardRequest(
    val id: String,
    val name: String,
    val date: String,
    val rewardName: String,
)