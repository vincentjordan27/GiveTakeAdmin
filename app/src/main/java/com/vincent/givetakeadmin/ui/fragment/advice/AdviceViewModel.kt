package com.vincent.givetakeadmin.ui.fragment.advice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vincent.givetakeadmin.data.repository.advice.AdviceRepository
import com.vincent.givetakeadmin.data.source.request.ReplyAdviceRequest
import com.vincent.givetakeadmin.data.source.response.StatusResponse
import com.vincent.givetakeadmin.data.source.response.advice.AllAdviceResponse
import com.vincent.givetakeadmin.utils.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AdviceViewModel(private val repository: AdviceRepository) : ViewModel() {
    val allAdviceResult = MutableLiveData<Result<AllAdviceResponse?>>()
    val replyAdviceResult = MutableLiveData<Result<StatusResponse?>>()

    fun getAllAdvice() = viewModelScope.launch {
        repository.getAllAdvices().collect {
            allAdviceResult.value = it
        }
    }

    fun replyAdvice(id: String, body: ReplyAdviceRequest) = viewModelScope.launch {
        repository.replyAdvice(id, body).collect {
            replyAdviceResult.value = it
        }
    }
}