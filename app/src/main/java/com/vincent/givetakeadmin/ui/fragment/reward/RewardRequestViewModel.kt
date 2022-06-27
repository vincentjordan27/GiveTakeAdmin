package com.vincent.givetakeadmin.ui.fragment.reward

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vincent.givetakeadmin.data.repository.reward.RewardRepository
import com.vincent.givetakeadmin.data.source.response.StatusResponse
import com.vincent.givetakeadmin.data.source.response.request.AllRewardsRequestResponse
import com.vincent.givetakeadmin.data.source.response.reward.AllRewardsResponse
import com.vincent.givetakeadmin.data.source.response.reward.RewardItem
import com.vincent.givetakeadmin.utils.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RewardRequestViewModel(private val repository: RewardRepository): ViewModel() {
    val allRewardRequestResult = MutableLiveData<Result<AllRewardsRequestResponse?>>()
    val finishRequestRewardResult = MutableLiveData<Result<StatusResponse?>>()

    fun getAllRewardsRequest() = viewModelScope.launch {
        repository.getRewardsRequest().collect {
            allRewardRequestResult.value = it
        }
    }

    fun finishRequestReward(id: String) = viewModelScope.launch {
        repository.finishRewardRequest(id).collect {
            finishRequestRewardResult.value = it
        }
    }
}