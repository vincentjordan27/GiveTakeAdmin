package com.vincent.givetakeadmin.ui.activity.reward

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vincent.givetakeadmin.data.repository.reward.RewardRepository
import com.vincent.givetakeadmin.data.source.request.AddRewardRequest
import com.vincent.givetakeadmin.data.source.response.StatusResponse
import com.vincent.givetakeadmin.data.source.response.reward.UploadImageRewardResponse
import com.vincent.givetakeadmin.utils.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File

class RewardViewModel(private val repository: RewardRepository) : ViewModel() {
    val addRewardRequest = MutableLiveData<Result<StatusResponse?>>()
    val uploadImageRewardResult = MutableLiveData<Result<UploadImageRewardResponse?>>()

    fun addReward(body: AddRewardRequest) = viewModelScope.launch {
        repository.addReward(body).collect {
            addRewardRequest.value = it
        }
    }

    fun uploadImageReward(file: File) = viewModelScope.launch {
        repository.uploadImageReward(file).collect {
            uploadImageRewardResult.value = it
        }
    }
}