package com.vincent.givetakeadmin.data.source.api

import com.vincent.givetakeadmin.data.source.response.advice.AllAdviceResponse
import retrofit2.Response
import retrofit2.http.GET

interface AdviceService {
    @GET("advices")
    suspend fun getAllAdvices() : Response<AllAdviceResponse>
}