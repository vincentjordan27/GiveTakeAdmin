package com.vincent.givetakeadmin.data.source.response.advice

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class AllAdviceResponse(
    val status: String,
    val message: String,
    val data: List<AdviceItem>
)

@Parcelize
data class AdviceItem(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val reply: String,
    val name: String,
    @field:SerializedName("addDate")
    val date: String
) : Parcelable