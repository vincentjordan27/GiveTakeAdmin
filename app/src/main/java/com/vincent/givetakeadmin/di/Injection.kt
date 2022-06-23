package com.vincent.givetakeadmin.di

import com.vincent.givetakeadmin.data.repository.reward.RewardRepository
import com.vincent.givetakeadmin.data.source.ApiClient

object Injection {
    fun provideRewardRepository() : RewardRepository {
        val apiService = ApiClient.getRewardService()
        return RewardRepository.getInstance(apiService)
    }
}