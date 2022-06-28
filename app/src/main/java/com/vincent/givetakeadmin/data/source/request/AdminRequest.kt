package com.vincent.givetakeadmin.data.source.request

data class AdminLoginRequest(
    val username: String,
    val password: String
)

data class AdminOtpRequest(
    val code: String,
    val email: String
)