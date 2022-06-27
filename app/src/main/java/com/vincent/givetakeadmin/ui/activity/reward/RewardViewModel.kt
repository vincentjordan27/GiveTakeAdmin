package com.vincent.givetakeadmin.ui.activity.reward

import android.os.Build.VERSION_CODES.M
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vincent.givetakeadmin.data.repository.reward.RewardRepository
import com.vincent.givetakeadmin.data.source.request.AddUpdateRewardRequest
import com.vincent.givetakeadmin.data.source.response.StatusResponse
import com.vincent.givetakeadmin.data.source.response.reward.RewardDetailResponse
import com.vincent.givetakeadmin.data.source.response.reward.UploadImageRewardResponse
import com.vincent.givetakeadmin.utils.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File

class RewardViewModel(private val repository: RewardRepository) : ViewModel() {
    val addRewardRequest = MutableLiveData<Result<StatusResponse?>>()
    val updateRewardResult = MutableLiveData<Result<StatusResponse?>>()
    val detailRewardResult = MutableLiveData<Result<RewardDetailResponse?>>()
    val uploadImageRewardResult = MutableLiveData<Result<UploadImageRewardResponse?>>()
    val deleteRewardResult = MutableLiveData<Result<StatusResponse?>>()

    fun addReward(body: AddUpdateRewardRequest) = viewModelScope.launch {
        repository.addReward(body).collect {
            addRewardRequest.value = it
        }
    }

    fun updateReward(id: String, body: AddUpdateRewardRequest) = viewModelScope.launch {
        repository.updateReward(id, body).collect {
            updateRewardResult.value = it
        }
    }

    fun rewardById(id: String) = viewModelScope.launch {
        repository.getRewardDetail(id).collect {
            repository.getRewardDetail(id).collect {
                detailRewardResult.value = it
            }
        }
    }

    fun uploadImageReward(file: File) = viewModelScope.launch {
        repository.uploadImageReward(file).collect {
            uploadImageRewardResult.value = it
        }
    }

    fun deleteReward(id: String) = viewModelScope.launch {
        repository.deleteReward(id).collect {
            deleteRewardResult.value = it
        }
    }
}