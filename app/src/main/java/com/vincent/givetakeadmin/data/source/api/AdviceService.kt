package com.vincent.givetakeadmin.data.source.api

import com.vincent.givetakeadmin.data.source.request.ReplyAdviceRequest
import com.vincent.givetakeadmin.data.source.response.StatusResponse
import com.vincent.givetakeadmin.data.source.response.advice.AllAdviceResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AdviceService {
    @GET("advices")
    suspend fun getAllAdvices() : Response<AllAdviceResponse>

    @POST("reply/{id}")
    suspend fun replyAdvice(
        @Path("id") id: String,
        @Body body: ReplyAdviceRequest
    ) : Response<StatusResponse>
}