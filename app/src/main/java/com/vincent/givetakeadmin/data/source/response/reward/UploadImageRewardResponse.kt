package com.vincent.givetakeadmin.data.source.response.reward

data class UploadImageRewardResponse(
    val status: String,
    val data: UploadImageReward,
    val message: String
)

data class UploadImageReward(
    val url: String
)
