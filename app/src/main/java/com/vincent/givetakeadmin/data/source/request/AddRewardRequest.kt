package com.vincent.givetakeadmin.data.source.request

data class AddRewardRequest(
    val name: String,
    val photo: String,
    val desc: String,
    val price: Int,
    val stock: Int
)