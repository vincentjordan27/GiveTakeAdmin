package com.vincent.givetakeadmin.data.source.response.advice

import com.google.gson.annotations.SerializedName

data class AllAdviceResponse(
    val status: String,
    val message: String,
    val data: List<AdviceItem>
)

data class AdviceItem(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val reply: String,
    val name: String,
    @field:SerializedName("addDate")
    val date: String
)