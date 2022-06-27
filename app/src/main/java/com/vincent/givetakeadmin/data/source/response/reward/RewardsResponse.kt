package com.vincent.givetakeadmin.data.source.response.reward

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class AllRewardsResponse(
    val status: String,
    val data: List<RewardItem>,
    val message: String,
)

data class RewardDetailResponse(
    val status: String,
    val data: RewardItem,
    val message: String,
)

@Parcelize
data class RewardItem(
    val id: String,
    val name: String,
    val photo: String,
    @field:SerializedName("desc_reward")
    val desc: String,
    val price: Int,
    val stock: Int,
) : Parcelable
