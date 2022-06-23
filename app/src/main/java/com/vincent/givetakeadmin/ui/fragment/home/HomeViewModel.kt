package com.vincent.givetakeadmin.ui.fragment.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vincent.givetakeadmin.data.repository.reward.RewardRepository
import com.vincent.givetakeadmin.data.source.response.reward.AllRewardsResponse
import com.vincent.givetakeadmin.utils.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val rewardRepository: RewardRepository): ViewModel() {
    val allRewardsResult = MutableLiveData<Result<AllRewardsResponse?>>()

    fun getAllRewards() = viewModelScope.launch {
        rewardRepository.getRewards().collect {
            allRewardsResult.value = it
        }
    }
}