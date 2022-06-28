package com.vincent.givetakeadmin.data.source.api

import com.vincent.givetakeadmin.data.source.request.AdminLoginRequest
import com.vincent.givetakeadmin.data.source.request.AdminOtpRequest
import com.vincent.givetakeadmin.data.source.response.StatusResponse
import com.vincent.givetakeadmin.data.source.response.admin.AdminLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AdminService {
    @POST("admin/login")
    suspend fun loginAdmin(
        @Body body: AdminLoginRequest
    ) : Response<AdminLoginResponse>

    @POST("admin/otp")
    suspend fun sendOtpAdmin(
        @Body body: AdminOtpRequest
    ): Response<StatusResponse>
}