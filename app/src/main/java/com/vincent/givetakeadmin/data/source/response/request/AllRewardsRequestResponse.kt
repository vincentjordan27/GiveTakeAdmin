package com.vincent.givetakeadmin.data.source.response.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class AllRewardsRequestResponse(
    val status: String,
    val message: String,
    val data: List<RewardRequest>
)

@Parcelize
data class RewardRequest(
    val id: String,
    val name: String,
    val date: String,
    val rewardName: String,
    val photo: String,
    @field:SerializedName("desc_redeem")
    val desc: String,
) : Parcelable